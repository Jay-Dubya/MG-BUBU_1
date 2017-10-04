package com.sml.burberry.controller;

import com.sml.burberry.dao.BurberryDAO;
import com.sml.burberry.dao.BurberryShoeRepository;
import com.sml.burberry.model.GenericShoe;
import com.sml.burberry.model.Criteria;
import com.sml.burberry.model.ExtractDetail;
import com.sml.burberry.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.util.*;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Created by michaelgoode on 29/11/2016.
 */

@Controller
@RequestMapping("/burberry")
public class BurberryController {

    @Autowired
    private BurberryShoeRepository burberryShoeRepository;

    @Autowired
    private BurberryDAO burberryDAO;

    @RequestMapping(value = "/", method= RequestMethod.GET)
    public ModelAndView index() {
        System.out.println("Burberry::Controller::index");
        return new ModelAndView("burberry/index");
    }

    @RequestMapping(value = "/addshoe", method=RequestMethod.GET)
    public ModelAndView showform() {
        return new ModelAndView("burberry/addshoe","command", new GenericShoe());
    }

    @RequestMapping(value = "/saveshoe", method = RequestMethod.GET)
    public ModelAndView addShoe(@ModelAttribute("shoe") GenericShoe genericShoe) {
        System.out.println("BurberryController::saveshoe()");
        burberryShoeRepository.saveShoe(genericShoe.getImagecode(), genericShoe.getImagename());
        ModelAndView modelAndView = new ModelAndView("burberry/addshoe","command",new GenericShoe());
        modelAndView.addObject("shoe", genericShoe);
        return modelAndView;
    }

    @RequestMapping(value = "/findshoe", method = RequestMethod.GET)
    public ModelAndView getShoe(@ModelAttribute("shoe") GenericShoe genericShoe) {
        System.out.println("BurberryController::findShoe() " + genericShoe.getImagecode());
        List<GenericShoe> list = null;
        if (( genericShoe.getImagecode() != null ) || ( genericShoe.getImagename() != null )) {
            if (genericShoe.getSearch().equalsIgnoreCase("reference")) {
                list = burberryShoeRepository.findShoeByReference(genericShoe.getImagecode());
            } else {
                list = burberryShoeRepository.findShoeByImage(genericShoe.getImagecode());
            }
        }
        ModelAndView modelAndView = new ModelAndView("burberry/findshoe","command",new GenericShoe());
        modelAndView.addObject("list", list);
        modelAndView.addObject("shoe", genericShoe);
        return modelAndView;
    }

    @RequestMapping(value="uploadbulkfile", method = RequestMethod.GET)
    public ModelAndView uploadreport(Model model) {
        return new ModelAndView("uploadbulk", "command", new Report());
    }

    @RequestMapping( value = "extract", method = RequestMethod.GET)
    public ModelAndView extract() {
        return new ModelAndView("extract", "command", new Criteria() );
    }

    @RequestMapping( value = "runextract", method = RequestMethod.GET)
    public ModelAndView dataExtract(@ModelAttribute("criteria") Criteria criteria) {
        System.out.println(criteria.getInputFolder());
        System.out.println(criteria.toString());
        List<ExtractDetail> list = burberryDAO.search(criteria.getInputFolder(), criteria.getOutputFolder(),criteria.getField1(), criteria.getValue1(), criteria.getNewvalue1(), "" );

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("list", list);
        modelAndView.setViewName("extractresults");
        return modelAndView;
    }

    @RequestMapping(value = "uploadshoefile", method = RequestMethod.POST)
    public String uploadFileHandler(@RequestParam("file") MultipartFile file) {
        final String folderName = "Burberry";
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                // Creating the directory to store file
                String rootPath = System.getProperty("catalina.home");
                File dir = new File(rootPath + File.separator + "tmpFiles" + File.separator + folderName);
                if (!dir.exists())
                    dir.mkdirs();
                // Create the file on server
                String[] names = dir.list();
                List<String> lstFiles = Arrays.asList(names);
                if (!lstFiles.contains(file.getOriginalFilename())) {
                    File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
                    BufferedOutputStream stream = new BufferedOutputStream(
                            new FileOutputStream(serverFile));
                    stream.write(bytes);
                    stream.close();
                    HashSet<GenericShoe> items = processBurberryXLFile(serverFile.getAbsolutePath());
                    if ( items != null ) {
                        burberryShoeRepository.postBatch(items, file.getOriginalFilename());
                    }
                    return "index";
                } else return "You failed to upload " + file.getOriginalFilename() + " => previously uploaded";

            } catch (Exception e) {
                return "You failed to upload " + file.getOriginalFilename() + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + file.getOriginalFilename()
                    + " because the file was empty.";
        }
    }

    public HashSet<GenericShoe> processBurberryXLFile(String path ) {
        HashSet<GenericShoe> items = new HashSet<GenericShoe>();
        Workbook workbook = null;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(path));
            workbook = new XSSFWorkbook(inputStream);
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = firstSheet.iterator();
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                DataFormatter df = new DataFormatter();
                String code = df.formatCellValue(nextRow.getCell(0));
                String image = df.formatCellValue(nextRow.getCell(1));
                if (!code.equals("")) {
                    GenericShoe genericShoe = new GenericShoe();
                    genericShoe.setImagecode(code);
                    genericShoe.setImagename(image.toUpperCase());
                    items.add(genericShoe);
                }
            }
        } catch (Exception ex) {
            return null;
        }

        finally {
            try {
                workbook.close();
                inputStream.close();
            } catch (IOException ex) {
                return null;
            }
            return items;
        }
    }

    public HashSet<GenericShoe> processBurberryShoeFile(String path ) {
        HashSet<GenericShoe> items = new HashSet<GenericShoe>();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {
            GenericShoe shoe = null;
            br = new BufferedReader(new FileReader(path));
            String top = br.readLine();
            while (((line = br.readLine()) != null) && (!top.isEmpty())) {
                // use comma as separator
                String[] cells = line.split(cvsSplitBy);
                if (!cells[0].trim().equals("")) {
                    shoe = new GenericShoe();
                    shoe.setImagecode(cells[0].trim().toUpperCase());
                    shoe.setImagename(cells[1].trim().toUpperCase());
                    items.add(shoe);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (br != null) {
                try {
                    br.close();
                    return items;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                return null;
            }
        }
    }
}

