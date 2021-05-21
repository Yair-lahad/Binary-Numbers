/*
I, Yair Lahad(205493018), assert that the work I submitted is entirely my own.
I have not received any part from any other student in the class,
nor did I give parts of it for use to others.
I realize that if my work is found to contain code that is not originally my own,
 a formal case will be opened against me with the BGU disciplinary committee.
*/

import com.sun.corba.se.spi.extension.ZeroPortPolicy;

import java.util.Iterator;

public class BinaryNumber implements Comparable<BinaryNumber> {
    private static final BinaryNumber ZERO = new BinaryNumber(0);
    private static final BinaryNumber ONE = new BinaryNumber(1);
    private BitList bits;

    // Copy constructor
    //Do not chainge this constructor
    public BinaryNumber(BinaryNumber number) {
        bits = new BitList(number.bits);
    }

    //Do not chainge this constructor
    private BinaryNumber(int i) {
        bits = new BitList();
        bits.addFirst(Bit.ZERO);
        if (i == 1)
            bits.addFirst(Bit.ONE);
        else if (i != 0)
            throw new IllegalArgumentException("This Constructor may only get either zero or one.");
    }

    //Do not chainge this method
    public int length() {
        return bits.size();
    }

    //Do not change this method
    public boolean isLegal() {
        return bits.isNumber() & bits.isReduced();
    }

    //=========================== Intro2CS 2020, ASSIGNMENT 4, TASK 3.1 ================================================
    public BinaryNumber(char c) {
        int Cval = c - '0';
        if (Cval > 9 | Cval < 0)
            throw new IllegalArgumentException(c + " is not a decimal digit");
        bits = new BitList();
        while (Cval != 0) {
            int r = Cval % 2;
            bits.addLast(new Bit(r));
            Cval /= 2;
        }
        bits.addLast(new Bit(0));
    }

    //=========================== Intro2CS 2020, ASSIGNMENT 4, TASK 3.2 ================================================
    public String toString() {
        // Do not remove or change the next two lines
        if (!isLegal()) // Do not change this line
            throw new RuntimeException("I am illegal.");// Do not change this line
        //
        String output = bits.toString();
        return output.substring(1, output.length() - 1);
    }

    //=========================== Intro2CS 2020, ASSIGNMENT 4, TASK 3.3 ================================================
    public boolean equals(Object other) {
        boolean isEqual;
        BinaryNumber myNum = new BinaryNumber(this);
        if (!(other instanceof BinaryNumber))
            isEqual = false;
        else if (((BinaryNumber) other).length() != myNum.length())
            isEqual = false;
        else {
            Iterator<Bit> iterme = myNum.bits.iterator();
            Iterator<Bit> iterother = ((BinaryNumber) other).bits.iterator();
            isEqual = true;
            while (iterme.hasNext() & isEqual) {
                isEqual = iterme.next().equals(iterother.next());
            }
        }
        return isEqual;
    }

    //=========================== Intro2CS 2020, ASSIGNMENT 4, TASK 3.4 ================================================
    public BinaryNumber add(BinaryNumber addMe) {
        if (!addMe.isLegal())
            throw new IllegalArgumentException("not a legal input");
        BinaryNumber mynum = new BinaryNumber(this);
        int max = Math.max(length(), addMe.length()); //making both number the same size
        mynum.bits.padding(max);
        addMe.bits.padding(max);
        Iterator<Bit> itme = mynum.bits.iterator();
        Iterator<Bit> itaddme = addMe.bits.iterator();
        Bit sum = new Bit(0);
        Bit c = new Bit(0);
        BinaryNumber result = new BinaryNumber(ZERO);
        result.bits.removeFirst(); // empty new Binary number
        // adding to result
        while (itme.hasNext()) {
            Bit b1 = itme.next();
            Bit b2 = itaddme.next();
            sum = Bit.fullAdderSum(b1, b2, c);
            c = Bit.fullAdderCarry(b1, b2, c);
            result.bits.addLast(sum);
        }
        if (mynum.bits.getLast().equals(addMe.bits.getLast())) {
            result.bits.addLast(Bit.fullAdderCarry(mynum.bits.getLast(), addMe.bits.getLast(), c));
        }
        result.bits.reduce();
        return result;
    }

    //=========================== Intro2CS 2020, ASSIGNMENT 4, TASK 3.5 ================================================
    public BinaryNumber negate() {
        BinaryNumber negate = new BinaryNumber(this);
        negate.bits = negate.bits.complement(); // negate each bit
        // makes the Number with same |value| but switches sign:
        negate = negate.add(new BinaryNumber('1'));
        return negate;
    }

    //=========================== Intro2CS 2020, ASSIGNMENT 4, TASK 3.6 ================================================
    public BinaryNumber subtract(BinaryNumber subtractMe) {
        if (!subtractMe.isLegal())
            throw new IllegalArgumentException("not a legal input");
        BinaryNumber comp = new BinaryNumber(subtractMe);
        comp = comp.negate();
        comp = add(comp);
        return comp;
    }

    //=========================== Intro2CS 2020, ASSIGNMENT 4, TASK 3.7 ================================================
    public int signum() {
        int output = 1;
        if (length() == 1 & bits.getLast().equals(Bit.ZERO)) output = 0;
        else if (bits.getLast().equals(Bit.ONE)) output = -1;
        return output;
    }

    //=========================== Intro2CS 2020, ASSIGNMENT 4, TASK 3.8 ================================================
    public int compareTo(BinaryNumber other) {
        if (!other.isLegal())
            throw new IllegalArgumentException("not a legal input");
        return this.subtract(other).signum();
    }

    //=========================== Intro2CS 2020, ASSIGNMENT 4, TASK 3.9 ================================================
    public int toInt() {
        // Do not remove or change the next two lines
        if (!isLegal()) // Do not change this line
            throw new RuntimeException("I am illegal.");// Do not change this line
        //
        if (length() > 32)
            throw new RuntimeException("Result out of bounds");
        BinaryNumber mynum = new BinaryNumber(this);
        int output = 0;
        int index = 0;
        int pow = 1;
        if (mynum.signum() > 0) {
            //representaion of positive number
            for (Bit element : mynum.bits) {
                for (int i = 0; i < index; i++)
                    pow = pow * 2;
                output = output + element.toInt() * pow;
                index++;
                pow = 1;
            }
        } else {
            //representaion of negetive number
            mynum = mynum.negate();
            for (Bit element : mynum.bits) {
                for (int i = 0; i < index; i++)
                    pow = pow * 2;
                output = output + element.toInt() * pow;
                index++;
                pow = 1;
            }
            output = output * -1;
        }
        return output;
    }

    //=========================== Intro2CS 2020, ASSIGNMENT 4, TASK 3.10 ================================================
    private int bothSign(BinaryNumber b1, BinaryNumber b2) { // checking if numbers are both positive or negative
        int result = 0; // 0 meaning they have different signs
        if (b1.signum() == b2.signum() & b1.signum() > 0)
            result = 1;
        else if (b1.signum() == b2.signum() & b1.signum() < 0)
            result = -1;
        return result;
    }

    // Do not change this method
    public BinaryNumber multiply(BinaryNumber multiplyMe) {
        BinaryNumber mynum = new BinaryNumber(this);
        BinaryNumber result;
        int bothSign = bothSign(mynum, multiplyMe); //returns 1 if both positive, -1 if both negetive or 0 if diffrent
        if (bothSign == 0) { // they have diffrent signs
            if (mynum.signum() < 0) {
                result = mynum.negate().multiplyPositive(multiplyMe);
            } else {
                result = mynum.multiplyPositive(multiplyMe.negate());
            }
            result = result.negate();
        } else {
            //they have the same Sign
            if (bothSign > 0) { // positive sign
                result = mynum.multiplyPositive(multiplyMe);
            } else { // negative sign
                result = mynum.negate().multiplyPositive(multiplyMe.negate());
                result = result.negate();
            }
        }
        return result;
    }

    private BinaryNumber multiplyPositive(BinaryNumber multiplyMe) {
        BinaryNumber result = new BinaryNumber(ZERO);
        BinaryNumber mynum = new BinaryNumber(this);
        Iterator<Bit> itMult = multiplyMe.bits.iterator();
        while (itMult.hasNext()) {
            Bit digit = itMult.next();
            // sum if d=1
            if (digit.equals(Bit.ONE)) {
                result = result.add(mynum);
            }
            mynum = mynum.multBy2();
        }
        return result;
    }

    //=========================== Intro2CS 2020, ASSIGNMENT 4, TASK 3.11 ================================================
    // Do not change this method
    public BinaryNumber divide(BinaryNumber divisor) {
        // Do not remove or change the next two lines
        if (divisor.equals(ZERO)) // Do not change this line
            throw new RuntimeException("Cannot divide by zero."); // Do not change this line
        //
        throw new UnsupportedOperationException("Delete this line and implement the method.");
    }

    private BinaryNumber dividePositive(BinaryNumber divisor) {
        throw new UnsupportedOperationException("Delete this line and implement the method.");
    }

    //=========================== Intro2CS 2020, ASSIGNMENT 4, TASK 3.12 ================================================
    public BinaryNumber(String s) {
        if (s == null)
            throw new IllegalArgumentException("cannot create binary number from null");
        if (s.isEmpty())
            throw new IllegalArgumentException("cannot create binary number from empty string");
        BinaryNumber current = new BinaryNumber(s.charAt(s.length() - 1));
        if (!current.isLegal())
            throw new IllegalArgumentException("not a legal input");
        BinaryNumber result = new BinaryNumber(current);
        BinaryNumber ten = new BinaryNumber('9');
        BinaryNumber one= new BinaryNumber('1');
        BinaryNumber zero=new BinaryNumber('0');
        ten = ten.add(one);
        boolean isneg = false;
        for (int i = s.length() - 2; i >= 0 & !isneg; i--) {
            if (s.startsWith("-")) {
                if (i == 0){ // get out of for but remmeber to convert to negetive number
                    isneg = true;
                }
                else {
                    current = new BinaryNumber(s.charAt(i));
                    if(current.equals(zero)){
                        current=one;
                        current = current.multiply(ten);
                        current=current.subtract(one);
                    }
                    else current = current.multiply(ten);
                    result = result.add(current);
                }
            }
            else {
                current = new BinaryNumber(s.charAt(i));
                current = current.multiply(ten);
                result = result.add(current);
            }
        }
        if (isneg) result = result.negate();
        this.bits = result.bits;
    }

    //=========================== Intro2CS 2020, ASSIGNMENT 4, TASK 3.13 ================================================
    public String toIntString() {
        // Do not remove or change the next two lines
        if (!isLegal()) // Do not change this line
            throw new RuntimeException("I am illegal.");// Do not change this line
        //
        throw new UnsupportedOperationException("Delete this line and implement the method.");
    }


    // Returns this * 2
    public BinaryNumber multBy2() {
        BinaryNumber output = new BinaryNumber(this);
        output.bits.shiftLeft();
        output.bits.reduce();
        return output;
    }

    // Returens this / 2;
    public BinaryNumber divBy2() {
        BinaryNumber output = new BinaryNumber(this);
        if (!equals(ZERO)) {
            if (signum() == -1) {
                output.negate();
                output.bits.shiftRight();
                output.negate();
            } else output.bits.shiftRight();
        }
        return output;
    }
}
