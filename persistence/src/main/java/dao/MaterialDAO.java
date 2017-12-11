package dao;

import entity.Material;
import org.springframework.stereotype.Service;

@Service
public class MaterialDAO extends AbstractDAO<Material> {
    @Override
    public Class<Material> getEntityClass() {
        return Material.class;
    }
}
