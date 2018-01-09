package service;

import dao.MaterialDAO;
import dto.MaterialDTO;
import dto.mapper.MaterialMapper;
import entity.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaterialService {

    @Autowired
    private MaterialDAO materialDAO;

    private MaterialMapper materialMapper = MaterialMapper.INSTANCE;

    public void createMaterial(MaterialDTO materialDTO) {
        Material  material = materialMapper.mapToNewEntity(materialDTO);
        materialDAO.persistEntity(material);
    }

    public MaterialDTO getMaterialById(Integer id) {
        return materialMapper.mapToDTO(materialDAO.findEntity(id));
    }
}
