package service;

import dao.TutorialMaterialDAO;
import entity.TutorialMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaterialService {

    @Autowired
    private TutorialMaterialDAO tutorialMaterialDAO;

    public void deleteTutorial(Integer materialId) {
        TutorialMaterial tutorialMaterial = tutorialMaterialDAO.findEntity(materialId);
        tutorialMaterialDAO.deleteEntity(tutorialMaterial);
    }
}
