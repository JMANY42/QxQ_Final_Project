import java.util.*;
public class Player
{
    private String name;
    private int[] basis;
    private int[] bits;
    private Integer[] key;
    private Integer[] keyCheckPortion;

    public Player(String n, int length)
    {
        name = n;
        basis = new int[length];
        bits = new int[length];

        randomizeBasis();
    }

    public String getName()
    {
        return name;
    }

    public int[] getBasis()
    {
        return basis;
    }

    public int[] getBits()
    {
        return bits;
    }

    public Integer[] getKey()
    {
        return key;
    }
    public Integer[] getKeyCheckPortion()
    {
        return keyCheckPortion;
    }

    public void randomizeBasis()
    {
        for(int i=0;i<basis.length;i++)
        {
            basis[i] = (int)(Math.random()*2);
        }
    }

    public void randomizeBits()
    {
        for(int i=0;i<basis.length;i++)
        {
            bits[i] = (int)(Math.random()*2);
        }
    }

    public void sendBit(Player other,int index)
    {
        if(basis[index]==other.getBasis()[index])
        {
            other.getBits()[index] = bits[index];
        }
        else{
            other.getBits()[index] = (int)(Math.random()*2);
        }
    }

    public void generateKey(Player other)
    {
        ArrayList<Integer> keyAL = new ArrayList<Integer>();
        ArrayList<Integer> keyPortionAL = new ArrayList<Integer>();


        for(int i=0;i<basis.length;i++)
        {
            if(basis[i]==other.getBasis()[i])
            {
                keyAL.add(bits[i]);
            }
        }
        for(int i=0;i<Math.ceil(keyAL.size()/5.0f);i++)
        {
            keyPortionAL.add(keyAL.remove(0));
        }
        key = new Integer[keyAL.size()];
        key = keyAL.toArray(key);
        keyCheckPortion = new Integer[keyPortionAL.size()];
        keyCheckPortion = keyPortionAL.toArray(keyCheckPortion);
    }
}