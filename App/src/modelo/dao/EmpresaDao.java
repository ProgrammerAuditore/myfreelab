package modelo.dao;

import java.util.ArrayList;
import java.util.List;
import modelo.dto.EmpresaDto;
import modelo.interfaces.keyword_query;

public class EmpresaDao implements keyword_query<EmpresaDto>{

    @Override
    public boolean mtdInsetar(EmpresaDto proyecto_dto) {
        return false;
    }

    @Override
    public boolean mtdEliminar(EmpresaDto proyecto_dto) {
        return false;
    }

    @Override
    public boolean mtdActualizar(EmpresaDto proyecto_dto) {
        return false;
    }

    @Override
    public boolean mtdConsultar(EmpresaDto proyecto_dto) {
        return false;
    }

    @Override
    public List<EmpresaDto> mtdListar() {
        List<EmpresaDto> empresas = new ArrayList<EmpresaDto>();
        
        return empresas;
    }

    @Override
    public boolean mtdComprobar(EmpresaDto proyecto_dto) {
        return false;
    }
    
}
