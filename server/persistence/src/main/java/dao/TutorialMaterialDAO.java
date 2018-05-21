package dao;

import entity.TutorialMaterial;
import org.springframework.stereotype.Repository;

@Repository
public class TutorialMaterialDAO extends AbstractDAO<TutorialMaterial> {
    @Override
    public Class<TutorialMaterial> getEntityClass() {
        return TutorialMaterial.class;
    }
}
