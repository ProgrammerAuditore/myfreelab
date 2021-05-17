package modelo.dao;

import java.util.ArrayList;
import java.util.List;
import modelo.dto.VinculacionDto;
import modelo.interfaces.keyword_query;

public class VinculacionDao implements keyword_query<VinculacionDto>{

    @Override
    public boolean mtdInsetar(VinculacionDto obj_dto) {
        return false;
    }

    @Override
    public boolean mtdEliminar(VinculacionDto obj_dto) {
        return false;
    }

    @Override
    public boolean mtdActualizar(VinculacionDto obj_dto) {
        return false;
    }

    @Override
    public boolean mtdConsultar(VinculacionDto obj_dto) {
        return false;
    }

    @Override
    public List<VinculacionDto> mtdListar() {
        List<VinculacionDto> vinculaciones = new ArrayList<>();
        
        
        return vinculaciones;
    }

    @Override
    public boolean mtdComprobar(VinculacionDto obj_dto) {
        return false;
    }
    
}
