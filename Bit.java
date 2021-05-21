/*
I, Yair Lahad (205493018), assert that the work I submitted is entirely my own.
I have not received any part from any other student in the class,
nor did I give parts of it for use to others.
I realize that if my work is found to contain code that is not originally my own,
 a formal case will be opened against me with the BGU disciplinary committee.
*/

public class Bit {

    private  boolean value;
    public static  final Bit ONE  = new Bit(true);
    public static  final Bit ZERO = new Bit(false);

    public Bit(boolean value) {
        this.value = value;
    }

    public Bit(int intValue) {
        if (intValue == 0)
            value = false;
        else {
            if (intValue == 1)
                value = true;
            else throw new IllegalArgumentException(value + " is neither 0 nor 1.");
        }
    }

    public String toString() {
        return "" + toInt();
    }

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof Bit))
            return false;
        else return value == ((Bit) obj).value;
    }

    public Bit negate() {
        Bit output;
        if (value)
            output = ZERO;
        else output = ONE;
        return output;
    }

    public int toInt() {
        int output;
        if(value)
            output = 1;
        else
            output = 0;
        return output;
    }

    //=========================== Intro2CS 2020, ASSIGNMENT 4, TASK 1.1 ================================================
    public static Bit fullAdderSum(Bit bit1, Bit bit2, Bit bit3) {
        Bit Sum= new Bit (false);
        if((bit1.toInt()+bit2.toInt()+bit3.toInt())%2!=0) // odd number of bits are 1, sum returns 1
            Sum=ONE;
        return Sum;

    }

    public static Bit fullAdderCarry(Bit bit1, Bit bit2, Bit bit3) {
        Bit Carry= new Bit (false);
        if((bit1.toInt()+bit2.toInt()+bit3.toInt())>1) // at least 2 bits are 1
            Carry=ONE;
        return Carry;
    }
}
