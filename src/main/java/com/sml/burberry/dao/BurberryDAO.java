package com.sml.burberry.dao;



import com.sml.burberry.model.ExtractDetail;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;

/**
 * Created by michaelgoode on 10/08/2017.
 */
@Repository
public interface BurberryDAO {
    public List<File> getFilesForSearch(String inputDir);
    public List<ExtractDetail> search(String inputDir, String outputDir, String field, String value, String newValue, String orderFile);
    public void writeToFile(String outputDir, ExtractDetail extractDetail);
}
