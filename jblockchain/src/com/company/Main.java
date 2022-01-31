package com.company;

import java.util.*;

public class Main {

    public static ArrayList<MyBlock> blockchain = new ArrayList<MyBlock>();
    public static int difficulty = 4;
    public static void main(String[] args) {
        MyBlock block1 = new MyBlock(0, null, "First Block"); //genesis block

        block1.minetheBlock(difficulty);
        blockchain.add(block1);
        System.out.println(block1.toString());
        System.out.println("Current Block isValid: " + validateBlock(block1,null));
        MyBlock block2 = new MyBlock(1, block1.currentHash, "Second Block");
        block2.minetheBlock(difficulty);
        blockchain.add(block2);
       // block2.data="Corrupted Data"; //Uncomment and corrupt the data
        System.out.println(block2.toString());
        System.out.println("Current Block isValid: " + validateBlock(block2, block1));
        System.out.println("Current Chain isValid: " +validateTheChain(blockchain));
    }


    public static boolean validateBlock(MyBlock newBlock, MyBlock   previousBlock) {
        if (previousBlock == null) { //The first block
            if (newBlock.index != 0) {
                return false;
            }
            if (newBlock.previousHash != null) {
                return false;
            }
            if (newBlock.currentHash == null ||
                    !newBlock.calculate_Hash().equals(newBlock.
                            currentHash)) {
                return false;
            }
            return true;
        } else { //other blocks
            if (newBlock != null) {
                if (previousBlock.index + 1 != newBlock.index) {
                    return false;
                }
                if (newBlock.previousHash == null ||
                        !newBlock.previousHash.equals(previousBlock.
                                currentHash)) {
                    return false;
                }
                if (newBlock.currentHash == null ||
                        !newBlock.calculate_Hash().equals(newBlock.currentHash)) {
                    return false;

                }
                return true;
            }
            return false;
        }


    }

    public static boolean validateTheChain(ArrayList<MyBlock> blockchain) {
        if (!validateBlock(blockchain.get(0), null)) {
            return false;
        }
        for (int i = 1; i < blockchain.size(); i++) {
            MyBlock currentBlock = blockchain.get(i);
            MyBlock previousBlock = blockchain.get(i - 1);
            if (!validateBlock(currentBlock, previousBlock)) {
                return false;
            }
        }
        return true;
    }

}
