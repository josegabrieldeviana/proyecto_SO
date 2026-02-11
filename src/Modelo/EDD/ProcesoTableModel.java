/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.EDD;

import Modelo.clasesSO.Proceso;
import javax.swing.table.AbstractTableModel;

/**
 * Este es un modelo de Jtable que sirve para las listas de cada uno de los
 * estados
 * La idea es que en el main se puedan añadir libremente procesos a las colas de
 * estados y que automaticamente las Jtable asociadas puedan actualizarse con la
 * nueva
 * información correspondiente.
 * 
 * @version 1
 * @param Lista<Proceso> procesos
 * @author joseg
 */
public class ProcesoTableModel extends AbstractTableModel {

    private final Lista<Proceso> procesos;
    private final String[] columnas = { "ID", "Prioridad", "Nombre", "MAR", "PC", "TRD" };

    public ProcesoTableModel(Lista<Proceso> procesos) {
        this.procesos = procesos;
    }

    @Override
    public int getRowCount() {
        return procesos != null ? procesos.size() : 0; // condicion ? valor_sicierto: valor sifalso. Si NO es nulo es
                                                       // procesos.size(), de otra forma es 0
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (procesos == null)
            return null;
        Proceso p = procesos.BuscarPosicion(rowIndex);
        if (p == null)
            return null;

        return switch (columnIndex) {
            case 0 -> p.getID();
            case 1 -> p.getPrioridad();
            case 2 -> p.getNombre();
            case 3 -> p.getMAR();
            case 4 -> p.getPC();
            case 5 -> p.getTiempoRestanteDeadline();
            default -> null;
        };
    }

    public void addProceso(Proceso p) {
        procesos.addLast(p);
        int row = procesos.size() - 1;
        fireTableRowsInserted(row, row);
    }

    public void deleteProceso(int rowIndex) {
        if (procesos.delete(rowIndex) != null) {
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    public void setRowCount(int rowCount) {
        if (procesos == null)
            return;
        int currentCount = procesos.size();

        if (rowCount <= 0) {
            procesos.vaciar();
            if (currentCount > 0) {
                fireTableDataChanged();
            }
        } else if (rowCount < currentCount) {
            // Truncar la lista
            while (procesos.size() > rowCount) {
                procesos.deleteLast();
            }
            fireTableRowsDeleted(rowCount, currentCount - 1);
        }
        // Si rowCount > currentCount, no hacemos nada por defecto
        // ya que no tenemos procesos "vacíos" para rellenar.
    }
}
