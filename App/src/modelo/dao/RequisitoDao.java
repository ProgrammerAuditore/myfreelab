package modelo.dao;

import java.util.ArrayList;
import java.util.List;
import modelo.dto.RequisitoDto;
import modelo.interfaces.keyword_query;

public class RequisitoDao implements keyword_query<RequisitoDto>{

    @Override
    public boolean mtdInsetar(RequisitoDto requisito_dto) {
        return false;
    }

    @Override
    public boolean mtdEliminar(RequisitoDto requisito_dto) {
        return false;
    }

    @Override
    public boolean mtdActualizar(RequisitoDto requisito_dto) {
        return false;
    }

    @Override
    public boolean mtdConsultar(RequisitoDto requisito_dto) {
        return false;
    }

    @Override
    public List<RequisitoDto> mtdListar() {
        List<RequisitoDto> requisitos = new ArrayList<>();
        
        return requisitos;
    }

    @Override
    public boolean mtdComprobar(RequisitoDto requisito_dto) {
        return false;
    }
    
}
