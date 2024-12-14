package it.unicam.cs.asdl2425.mp1;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

//TODO inserire gli import della Java SE che si ritengono necessari

/**
 * Una classe che rappresenta una lista concatenata con il calcolo degli hash
 * MD5 per ciascun elemento. Ogni nodo della lista contiene il dato originale di
 * tipo generico T e il relativo hash calcolato utilizzando l'algoritmo MD5.
 *
 * <p>
 * La classe supporta le seguenti operazioni principali:
 * <ul>
 * <li>Aggiungere un elemento in testa alla lista</li>
 * <li>Aggiungere un elemento in coda alla lista</li>
 * <li>Rimuovere un elemento dalla lista in base al dato</li>
 * <li>Recuperare una lista ordinata di tutti gli hash contenuti nella
 * lista</li>
 * <li>Costruire una rappresentazione testuale della lista</li>
 * </ul>
 *
 * <p>
 * Questa implementazione include ottimizzazioni come il mantenimento di un
 * riferimento all'ultimo nodo della lista (tail), che rende l'inserimento in
 * coda un'operazione O(1).
 *
 * <p>
 * La classe utilizza la classe HashUtil per calcolare l'hash MD5 dei dati.
 *
 * @param <T>
 *                il tipo generico dei dati contenuti nei nodi della lista.
 *
 * @author Luca Tesei, Marco Caputo (template) **INSERIRE NOME, COGNOME ED EMAIL
 *         xxxx@studenti.unicam.it DELLO STUDENTE** (implementazione)
 *
 */
public class HashLinkedList<T> implements Iterable<T> {
    private Node head; // Primo nodo della lista

    private Node tail; // Ultimo nodo della lista

    private int size; // Numero di nodi della lista

    private int numeroModifiche; // Numero di modifiche effettuate sulla lista
    // per l'implementazione dell'iteratore
    // fail-fast

    public HashLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
        this.numeroModifiche = 0;
    }

    /**
     * Restituisce il numero attuale di nodi nella lista.
     *
     * @return il numero di nodi nella lista.
     */
    public int getSize() {
        return size;
    }

    /**
     * Rappresenta un nodo nella lista concatenata.
     */
    private class Node {
        String hash; // Hash del dato

        T data; // Dato originale

        Node next;

        Node(T data) {
            this.data = data;
            this.hash = HashUtil.dataToHash(data);
            this.next = null;
        }
    }

    /**
     * Aggiunge un nuovo elemento in testa alla lista.
     *
     * @param data il dato da aggiungere.
     */
    public void addAtHead(T data) {
        // TODO implementare
        if(data == null) throw new NullPointerException("eccezione in addAtHead");
        Node newNode = new Node(data);
        if(head == null){       //se la lista è vuota head e tail coincidono
            head = newNode;
            tail = head;
        }
        else {                  //altrimenti scalo testa e sostituisco
            newNode.next = head;
            head = newNode;
        }
        size++;
        numeroModifiche++;
    }

    /**
     * Aggiunge un nuovo elemento in coda alla lista.
     *
     * @param data il dato da aggiungere.
     */
    public void addAtTail(T data) {
        // TODO implementare
        if(data == null) throw new NullPointerException("eccezione in addAtTail");
        Node newNode = new Node(data);
        // Se non esiste head la lista è vuota
        if (head == null) {
            head = newNode;
            tail = head;
        }
        else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
        numeroModifiche++;
    }

    /**
     * Restituisce un'ArrayList contenente tutti gli hash nella lista in ordine.
     *
     * @return una lista con tutti gli hash della lista.
     */
    public ArrayList<String> getAllHashes() {
        // TODO implementare
        ArrayList<String> hashes = new ArrayList<>();
        Iterator<T> itr = this.iterator();

        //forEach(T : hashes);
        while(itr.hasNext()){
            hashes.add(HashUtil.dataToHash(itr.next()));
        }
        return hashes;
    }

    /**
     * Costruisce una stringa contenente tutti i nodi della lista, includendo
     * dati e hash. La stringa dovrebbe essere formattata come nel seguente
     * esempio:
     *
     * <pre>
     *     Dato: StringaDato1, Hash: 5d41402abc4b2a76b9719d911017c592
     *     Dato: SteringaDato2, Hash: 7b8b965ad4bca0e41ab51de7b31363a1
     *     ...
     *     Dato: StringaDatoN, Hash: 2c6ee3d301aaf375b8f026980e7c7e1c
     * </pre>
     *
     * @return una rappresentazione testuale di tutti i nodi nella lista.
     */
    public String buildNodesString() {
        // TODO implementare
        Iterator<T> itr = this.iterator();

        StringBuilder str = new StringBuilder();
        while(itr.hasNext())
        {
            T data = itr.next();
            str.append("Dato: ").append(data).append(", ")
                    .append("Hash: ").append(HashUtil.dataToHash(data)).append("\n");

        }
        return str.toString();
    }

    /**
     * Rimuove il primo elemento nella lista che contiene il dato specificato.
     *
     * @param data il dato da rimuovere.
     * @return true se l'elemento è stato trovato e rimosso, false altrimenti.
     */
    public boolean remove(T data) {
        // TODO implementare
        if(data == null) throw new NullPointerException("nullPtr in remove");
        if(this.head == null) return false;         //lista nulla

        if(this.head.data.equals(data)){
            this.head = this.head.next;
            if(this.head == null)this.tail = null;
            size--;
            numeroModifiche++;
            return true;
        }

        Node current = head;
        while(current.next != null && !current.next.data.equals(data)){
            current = current.next;
        }

        if(current.next == null) return false;

        current.next = current.next.next;
        if(current.next == null) this.tail = current;

        size--;
        numeroModifiche++;
        return true;
    }


    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    /**
     * Classe che realizza un iteratore fail-fast per HashLinkedList.
     */
    private class Itr implements Iterator<T> {

        // TODO inserire le variabili istanza che si ritengono necessarie
        private Node lastReturned;
        private int numeroModificheAtteso;

        private Itr() {
            // TODO implementare
            // Next mai usato
            this.lastReturned = null;
            this.numeroModificheAtteso = HashLinkedList.this.numeroModifiche;
        }

        @Override
        public boolean hasNext() {
            // TODO implementare
            if (this.lastReturned == null)
                // inizio dell'iterazione
                return HashLinkedList.this.head != null;
            else
                // almeno un next
                return lastReturned.next != null;

        }

        @Override
        public T next() {
            // TODO implementare
            // controllo concorrenza
            if (this.numeroModificheAtteso != HashLinkedList.this.numeroModifiche) {
                throw new ConcurrentModificationException(
                        "Lista modificata durante l'iterazione");
            }
            // controllo hasNext()
            if (!this.hasNext())
                throw new NoSuchElementException(
                        "Richiesta di next quando hasNext è falso");
            // c'è sicuramente un elemento su cui fare next
            // aggiorno lastReturned e restituisco l'elemento next
            if (this.lastReturned == null) {
                // sono all’inizio e la lista non è vuota
                this.lastReturned = HashLinkedList.this.head;
                return HashLinkedList.this.head.data;
            } else {
                // non sono all’inizio, ma c’è ancora qualcuno
                lastReturned = lastReturned.next;
                return lastReturned.data;
            }
        }
    }

    // TODO inserire eventuali metodi privati per fini di implementazione
}