package modelo.interfaces;

import java.util.List;

public interface keyword_query <none> {
    
    public boolean mtdInsetar(none proyecto_dto);
    public boolean mtdEliminar(none proyecto_dto);
    public boolean mtdActualizar(none proyecto_dto);
    public boolean mtdConsultar(none proyecto_dto);
    public List<none> mtdListar();
    public boolean mtdComprobar(none proyecto_dto);
    
}
