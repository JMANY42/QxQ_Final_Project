/*
 *
 *
 * David Jonathan Lewis QxQ Final Project  Submission
 * 
 * This program simulates Quantum Key Distribution with an evesdropper and without an evesdropper *
 *
 *
*/

import java.util.*;
public class QuantumKeyDistribution
{
    public static void main(String[]args)
    {
        //gets Input
        Scanner in = new Scanner(System.in);
        System.out.println("Is there an eavesdropper? (y/n) ");
        boolean goodInput = false;
        String input = "";
        while(!goodInput)
        {
            input = in.nextLine();
            goodInput = input.equals("y")||input.equals("n");

            if(!goodInput)
            {
                System.out.println("please enter y or n ");
            }
        }

        boolean eavesdropper = input.equals("y");

        System.out.println("How many bits will alice send to bob? ");
        goodInput = false;
        int length = 0;
        while(!goodInput)
        {
            try{
                length = Integer.valueOf(in.nextLine());
            } catch (Exception e){
                System.out.println("Please enter a number");
                continue;
            }
            goodInput = true;
        }
        in.close();


        //creats the players
        Player alice = new Player("alice",length);
        Player bob = new Player("bob",length);
        Player eve = new Player("eve",length);

        //simulates alice sending the bits to bob
        alice.randomizeBits();
        for(int i=0;i<length;i++)
        {
            if(!eavesdropper)
            {
                //if no eavesdropper, alice send the bits straight to bob
                alice.sendBit(bob,i);
            }
            else
            {
                //if there is an eavesdropper, alice unknowlingly sends the qubits to eve who then sends the bits on to bob
                //But because of quantum superposition, eve can't know if the "1" or "0" she received was because a 1 or 0 was sent or if she chose the wrong basis

                alice.sendBit(eve,i);
                eve.sendBit(bob,i);
            }
        }

        //prints the basis and qubits used by alice, bob, and eve
        //0 represents horizontal basis
        //1 represents diagonal basis
        System.out.println("Alice basis: "+Arrays.toString(alice.getBasis()).replace("[","").replace("]","").replace(", ",""));
        if(eavesdropper)
        {
            System.out.println("Eve basis:   "+Arrays.toString(eve.getBasis()).replace("[","").replace("]","").replace(", ",""));
        }
        System.out.println("Bob basis:   "+Arrays.toString(bob.getBasis()).replace("[","").replace("]","").replace(", ",""));
        System.out.println("\nAlice bits:  "+Arrays.toString(alice.getBits()).replace("[","").replace("]","").replace(", ",""));
        if(eavesdropper)
        {
            System.out.println("Eve bits:    "+Arrays.toString(eve.getBits()).replace("[","").replace("]","").replace(", ",""));
        }
        System.out.println("Bob bits:    "+Arrays.toString(bob.getBits()).replace("[","").replace("]","").replace(", ",""));

        //alice and bob generate their key by throwing away qubits that were received with different bases
        alice.generateKey(bob);
        bob.generateKey(alice);

        //checks a portion of the key to check for errors
        int errorsFound = 0;
        if(alice.getKeyCheckPortion().length==bob.getKeyCheckPortion().length)
        {
            for(int i=0;i<alice.getKeyCheckPortion().length;i++)
            {
                if(alice.getKeyCheckPortion()[i].intValue()!=bob.getKeyCheckPortion()[i].intValue())
                {
                    errorsFound++;
                }
            }      
        }

        System.out.println("\nAlice Check Key:   "+Arrays.toString(alice.getKeyCheckPortion()).replace("[","").replace("]","").replace(", ",""));
        System.out.println("Bob Check Key:     "+Arrays.toString(bob.getKeyCheckPortion()).replace("[","").replace("]","").replace(", ",""));
        System.out.println("Errors Found: "+errorsFound);

        if(errorsFound==0)
        {
            //no errors were found so the rest of the key is guaranteed to be secret and can be used to encrypt and decrypt messages
            System.out.println("Key: "+Arrays.toString(alice.getKey()).replace("[","").replace("]","").replace(", ",""));
        }
        else
        {
            //at least one error was detected which means, at least in the simulation, that an eavesdropper was present
            System.out.println("Error(s) detected. Key Invalid.");
        }
    }
}