package service;

import dao.MaterialRepository;
import dto.MaterialDto;
import dto.mapper.MaterialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaterialService {
    private MaterialMapper materialMapper = MaterialMapper.INSTANCE;

    @Autowired
    private MaterialRepository materialRepository;


    public void deleteTutorial(Integer materialId) {
        materialRepository.delete(materialId);
    }

    public void saveMaterial(MaterialDto materialDto) {
        materialRepository.save(materialMapper.mapToNewEntity(materialDto));
    }

    public MaterialDto getMaterialById(Integer materialId) {
        return materialMapper.mapToDTO(materialRepository.findOne(materialId));
    }
}
