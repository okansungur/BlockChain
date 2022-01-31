package com.company;


import java.security.MessageDigest;
import java.util.ArrayList;

public class MyBlock {
    public int index;
    public long timestamp;
    public String currentHash;
    public String previousHash;
    public String data;
    public ArrayList<Transaction> transactions = new  ArrayList<Transaction>();
    public int nonce;
    public MyBlock(int index, String previousHash, ArrayList<Transaction>
            transactions) {
        this.index = index;
        this.timestamp = System.currentTimeMillis();
        this.previousHash = previousHash;
        this.transactions = transactions;
        nonce = 0;
        currentHash = calculate_Hash();
    }
    public String calculate_Hash(){
        try {

            data="";
            for (int j=0; j<transactions.size();j++){
                Transaction tr = transactions.get(j);
                data = data + tr.sender+tr.recipient+tr.value;
            }
            String input = index + timestamp + previousHash + data +
                    nonce;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void mineBlock(int difficulty) {
        nonce = 0;
        String target = new String(new char[difficulty]).replace('\0',
                '0');
        while (!currentHash.substring(0, difficulty).equals(target)) {
            nonce++;
            currentHash = calculate_Hash();
        }
    }
    public String toString() {
        String s = "Block  : " + index + "\r\n";
        s = s + "PreviousHash : " + previousHash + "\r\n";
        s = s + "Timestamp : " + timestamp + "\r\n";
        s = s + "Transactions : " + data + "\r\n";
        s = s + "Nonce : " + nonce + "\r\n";
        s = s + "CurrentHash : " +currentHash + "\r\n";
        return s;
    }
}