/*
I, Yair Lahad (205493018), assert that the work I submitted is entirely my own.
I have not received any part from any other student in the class,
nor did I give parts of it for use to others.
I realize that if my work is found to contain code that is not originally my own,
 a formal case will be opened against me with the BGU disciplinary committee.
*/

import java.util.Iterator;
import java.util.LinkedList;

public class BitList extends LinkedList<Bit> {
    private int numberOfOnes;

    // Do not change the constructor
    public BitList() {
        numberOfOnes = 0;
    }

    // Do not change the method
    public int getNumberOfOnes() {
        return numberOfOnes;
    }


//=========================== Intro2CS 2020, ASSIGNMENT 4, TASK 2.1 ================================================

    public void addLast(Bit element) {
        if (element==null)
            throw new IllegalArgumentException("Cannot add null");
        super.addLast(element);
        if (element.toInt()==1)
            numberOfOnes++;
    }

    public void addFirst(Bit element) {
        if (element==null)
            throw new IllegalArgumentException("Cannot add null");
        super.addFirst(element);
        if (element.toInt()==1)
            numberOfOnes++;

    }

    public Bit removeLast() {
        Bit garbage= super.removeLast();
        if(garbage==null)
            throw new IllegalArgumentException("cannot remove null");
        if(garbage.equals(Bit.ONE))
            numberOfOnes=numberOfOnes-1;
        return garbage;
    }

    public Bit removeFirst() {
        Bit garbage= super.removeFirst();
        if(garbage==null)
            throw new IllegalArgumentException("cannot remove null");
        if(garbage.equals(Bit.ONE))
            numberOfOnes=numberOfOnes-1;
        return garbage;
    }

    //=========================== Intro2CS 2020, ASSIGNMENT 4, TASK 2.2 ================================================
    public String toString() {
        if(this==null)
            throw new IllegalArgumentException("BitList can not be null");
        String output=">";
        if(size()==0){
            output="<"+output;
        }
        else{
            for(Bit element : this){
                output=element.toString()+output; // adding string from left to right
            }
            output="<"+output;
        }
        return output;
    }
    
    //=========================== Intro2CS 2020, ASSIGNMENT 4, TASK 2.3 ================================================
    public BitList(BitList other) {
        if (other==null)
            throw new IllegalArgumentException("cannot use null bitList");
        for(Bit element : other){
            this.addLast(element);
        }
    }

    //=========================== Intro2CS 2020, ASSIGNMENT 4, TASK 2.4 ================================================
    public boolean isNumber() {
        if(this==null)
            throw new IllegalArgumentException("can not check null");
        if(isEmpty())
            return false;
        boolean output=false;
        if(size()>=1){
            if(getLast().toInt()==0 ||numberOfOnes>1) output=true;
        }
        return output;
    }
    //=========================== Intro2CS 2020, ASSIGNMENT 4, TASK 2.5 ================================================
    private boolean isMin(BitList l){ // min values are <0> or <01> or <11>
        boolean output=false;
        BitList zero = new BitList();
        zero.addFirst(Bit.ZERO);
        BitList zero_one=new BitList();
        zero_one.addFirst(Bit.ZERO);
        zero_one.addFirst(Bit.ONE);
        BitList one_one=new BitList();
        one_one.addFirst(Bit.ONE);
        one_one.addFirst(Bit.ONE);
        if(l.equals(zero)|l.equals(zero_one)|l.equals(one_one))output=true;
        return output;
    }

    public boolean isReduced() {
        boolean output=false;
        BitList mynum=new BitList(this);
        if(mynum.isNumber()){
            if(size()<3){
                output=isMin(mynum); // created a function to check if represent min binary num.
            }
            else{
                //condtion B at least 3 bits and last are 01 or 10
                if(mynum.getLast().equals(Bit.ONE) & mynum.get(size()-2).equals(Bit.ZERO)||mynum.getLast().equals(Bit.ZERO)& mynum.get(size()-2).equals(Bit.ONE))
                    output=true;
                //condtion C at least 3 bits and last two are 11 and only them.
                if(mynum.getLast().equals(Bit.ONE) & mynum.get(size()-2).equals(Bit.ONE) & mynum.numberOfOnes==2)
                    output=true;
            }
        }
        return output;
    }

    public void reduce() {
        boolean output=isReduced();
        while(!output) {
            removeLast();
            output=isReduced();
        }
    }

    //=========================== Intro2CS 2020, ASSIGNMENT 4, TASK 2.6 ================================================
    public BitList complement() {
        BitList comp=new BitList();
        for(Bit element :this){
            comp.addLast(element.negate());
        }
        return comp;
    }

    //=========================== Intro2CS 2020, ASSIGNMENT 4, TASK 2.7 ================================================
    public Bit shiftRight() {
        if(size()==0)
            return null;
        Bit temp=getFirst();
        removeFirst();
        return temp;

    }

    public void shiftLeft() {
        addFirst(Bit.ZERO);
    }

    //=========================== Intro2CS 2020, ASSIGNMENT 4, TASK 2.8 ================================================
    public void padding(int newLength) {
        while(newLength > size()) addLast(getLast());
    }

    

    //----------------------------------------------------------------------------------------------------------
    // The following overriding methods must not be changed.
    //----------------------------------------------------------------------------------------------------------
    public boolean add(Bit e) {
        throw new UnsupportedOperationException("Do not use this method!");
    }

    public void add(int index, Bit element) {
        throw new UnsupportedOperationException("Do not use this method!");
    }

    public Bit remove(int index) {
        throw new UnsupportedOperationException("Do not use this method!");
    }

    public boolean offer(Bit e) {
        throw new UnsupportedOperationException("Do not use this method!");
    }

    public boolean offerFirst(Bit e) {
        throw new UnsupportedOperationException("Do not use this method!");
    }

    public boolean offerLast(Bit e) {
        throw new UnsupportedOperationException("Do not use this method!");
    }

    public Bit set(int index, Bit element) {
        throw new UnsupportedOperationException("Do not use this method!");
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Do not use this method!");
    }
}