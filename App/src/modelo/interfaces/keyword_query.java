package modelo.interfaces;

import java.util.List;

public interface keyword_query <none> {
    public boolean agregar(none c);
    public boolean actualizar(none c);
    public none consultar(Object key);
    public boolean eliminar(Object key);
    public List<none> listar();
}
