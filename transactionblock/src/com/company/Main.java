package com.company;

import java.time.Clock;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Main {

    public static ArrayList<MyBlock> blockchain = new ArrayList<MyBlock>();
    public static ArrayList<Transaction> transactions = new  ArrayList<Transaction>();
    public static int difficulty = 5;
    public static void main(String[] args) {
        Clock clock = Clock.systemDefaultZone();
        long beginMilliseconds = clock.millis();
        Wallet sender = new Wallet(blockchain);
        Wallet receiver = new Wallet(blockchain);
        System.out.println("Sender-wallet Balance: " + sender.getBalance());
        System.out.println("Receiver-wallet Balance: " + receiver.getBalance());

        System.out.println("Adding  transactions... ");
        Transaction tran1 = sender.send(receiver.publicKey, 10);
        if (tran1!=null){
            transactions.add(tran1);
        }
        Transaction tran2 = sender.send(receiver.publicKey, 20);
        if (tran2!=null){
            transactions.add(tran2);
        }
        MyBlock block1 = new MyBlock(0, null, transactions);
        block1.mineBlock(difficulty);
        blockchain.add(block1);
        System.out.println("Sender-wallet Balance: " + sender.getBalance());
        System.out.println("Receiver-wallet Balance: " + receiver.getBalance());
        System.out.println("Blockchain-validty : " +validateTheChain(blockchain));
        long endMilliseconds = clock.millis();
        long milis=endMilliseconds-beginMilliseconds;
        //System.out.println(milis);
        System.out.println("Total Time(seconds) :"+TimeUnit.MILLISECONDS.toSeconds(milis));

    }


    public static boolean validateBlock(MyBlock newBlock, MyBlock   previousBlock) {
        if (previousBlock == null) {
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
        } else {
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
