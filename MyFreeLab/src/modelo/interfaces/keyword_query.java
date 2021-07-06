package modelo.interfaces;

import java.util.List;

public interface keyword_query <none> {
    
    public boolean mtdInsetar(none obj_dto);
    public boolean mtdEliminar(none obj_dto);
    public boolean mtdRemover(none obj_dto);
    public boolean mtdActualizar(none obj_dto);
    public boolean mtdConsultar(none obj_dto);
    public List<none> mtdListar();
    public List<none> mtdListar(none dto);
    public boolean mtdComprobar(none obj_dto);
    
}
