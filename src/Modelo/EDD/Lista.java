/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.EDD;

/**
 *
 * @author JGDV
 * @param <T>
 */
public class Lista<T> {
    public Nodo<T> Head;
    private Nodo<T> Tail;

    public Lista() {
        this.Head = this.Tail = null;

    }

    /**
     *
     * @param n
     */
    public Lista(Nodo<T> n) {
        this.Head = this.Tail = n;

    }

    public boolean isEmpty() {
        return this.Head == null;
    }

    public int size() {
        int i = 0;

        if (isEmpty()) {
            return 0;
        }
        Nodo<T> aux = this.Head;
        while (aux != null) {
            aux = aux.getNext();
            i++;
        }
        return i;
    }

    /**
     *
     * @param dataL
     */
    public void addFirst(T dataL) {
        Nodo<T> n = new Nodo(dataL);
        if (isEmpty()) {
            this.Head = n;
            this.Tail = n;
            this.Head.setNext(this.Tail);
            this.Tail.setNext(null);
        } else {
            n.setNext(this.Head);
            this.Head = n;
        }
    }

    /**
     *
     * @param dataL
     */
    public void addLast(T dataL) {
        Nodo<T> n = new Nodo(dataL);
        if (isEmpty()) {
            this.Head = n;
            this.Tail = n;

        } else {
            this.Tail.setNext(n);
            this.Tail = n;
        }
    }

    public void add(T dataL, int i) {
        if (isEmpty() || i == 0) {
            this.addFirst(dataL);
        } else if (i >= (size() - 1)) {
            this.addLast(dataL);
        } else if (i < 0) {
            this.add(dataL, size() + i);
        } else {
            Nodo<T> n = new Nodo(dataL);
            Nodo aux = this.Head;
            int count = 0;
            while (count < i - 1) {
                aux = aux.getNext();
                count++;
            }
            n.setNext(aux.getNext());
            aux.setNext(n);
        }
    }

    public T deleteFirst() {
        if (isEmpty()) {
            return null;
        } else if (size() == 1) {
            this.Head = null;
            this.Tail = null;
            return null;
        } else {
            Nodo<T> temp = this.Head;
            this.Head = this.Head.getNext();
            temp.setNext(null);
            return temp.getData();
        }
    }

    public T deleteLast() {
        if (isEmpty()) {
            return null;
        }
        Nodo<T> pre = this.Head;
        while (pre.getNext().getNext() != null) {
            pre = pre.getNext();
        }
        Nodo<T> temp = pre.getNext();
        pre.setNext(null);
        this.Tail = pre;
        temp.setNext(null);
        return temp.getData();
    }

    public T delete(int i) {
        if (isEmpty()) {
            return null;
        } else if (i == 0) {
            return deleteFirst();
        } else if (i == size() - 1) {
            return deleteLast();
        } else if (i < 0) {
            return delete(size() + i);
        } else if (i > size() - 1) {
            System.out.println("\nNo Funciona");
            return null;
        } else {
            Nodo<T> aux = this.Head;
            int count = 0;
            while (count < i - 1) {
                aux = aux.getNext();
                count++;
            }
            Nodo<T> del = aux.getNext();
            aux.setNext(del.getNext());
            del.setNext(null);
            return del.getData();
        }
    }

    /**
     * 
     * @param i El índice del objeto que queremos conseguir.
     * @return el objeto que queremos obtener que corresponde al índice "i"
     *         insertado.
     */
    public T BuscarPosicion(int i) {
        if (isEmpty()) {
            return null;
        }
        Nodo<T> aux = this.Head;
        int count = 0;
        while (aux != null) {
            if (count == i) {
                return aux.getData();
            }
            aux = aux.getNext();
            count++;
        }
        return null;
    }

    public void print() {
        if (isEmpty()) {
            System.out.println("Lista Vacia");
        } else {
            Nodo aux = this.Head;
            int i = 0;
            while (aux != null) {
                System.out.print(aux.getData());
                aux = aux.getNext();
                i++;
            }
            System.out.println("");
        }
    }

    /**
     * 
     * @param dato
     * @return
     */
    public boolean buscar(T dato) {
        Nodo aux = this.Head;

        while (aux != null) {
            if (aux.getData().toString().equals(dato.toString())) {
                return true;
            }
        }
        return false;
    }

    public T buscarLast() {
        if (this.Head == null) {
            return null;
        }
        if (this.Head.getNext() == null) {
            return this.Head.getData();
        }

        Nodo<T> current = this.Head;
        while (current.getNext() != null) {
            current = current.getNext();
        }
        return current.getData();
    }

    /**
     * Imprime todas las strings de una Lista.
     * 
     * @return
     */
    public String printString() {
        String stringList = "";
        Nodo aux = this.Head;

        while (aux != null) {
            stringList += " " + aux.data;
            aux = aux.next;
        }
        return stringList;
    }

}
