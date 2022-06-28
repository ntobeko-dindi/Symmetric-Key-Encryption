package com.example.cypherus;

public class Encryption{
    private String key;
    private String cipherText;
    private String plainText;
    private final boolean isEncrypting;

    // Constructor
    public Encryption(String key, String text, boolean isEncrypting){
        this.isEncrypting = isEncrypting;
        initialiseTextProperties(text);
        sanitiseEncryptionKey(key);
    }

    //Initialise text props
    private void initialiseTextProperties(String text){
        if(isEncrypting)
            this.plainText = plainTextToHex(text);
        else
            this.cipherText = text;
    }

    //Prepare the encryption key before using it
    private void sanitiseEncryptionKey(String key){
        this.key = plainTextToHex(key);
    }

    //Convert ASCII values to their hex equivalents
    private String plainTextToHex(String text){
        char[] charArray = text.toCharArray();
        StringBuilder hexadecimal = new StringBuilder();
        for (char c : charArray) {
            hexadecimal.append(Integer.toHexString((int) c));
        }
        return hexadecimal.toString();
    }

    //Convert hex back to ASCII
    private String hexToPlainText(String hex){
        StringBuilder ASCIIText = new StringBuilder();
        for (int x = 0; x < hex.length(); x += 2){
            try{
                String tempStr = hex.substring(x, x + 2);
                ASCIIText.append((char) Integer.parseInt(tempStr, 16));
            }catch (Exception ignore){
                ASCIIText.setLength(0);
                ASCIIText.append("Couldn't decrypt the above text");
                break;
            }
        }
        return ASCIIText.toString();
    }

    // Calculate the sum of integer values in the hax code
    private int getSumOfHaxIntValues(){
        int sum = 0;
        for(int looper = 0; looper < key.length(); looper ++){
            String str = key.substring(looper, looper + 1);
            try {
                sum += Integer.parseInt(str);
            }
            catch (NumberFormatException ignored) {
            }
        }
        return sum;
    }

    //Encrypt plain text
    public String encrypt(){
        double haxSum = getSumOfHaxIntValues();
        StringBuilder sb = new StringBuilder();
        for(int looper = 0; looper < plainText.length(); looper ++){
            String str = plainText.substring(looper, looper + 1);
            try {
                double dummy = Integer.parseInt(str);
                sb.append("").append(dummy / haxSum).append("=");
            }
            catch (NumberFormatException e) {
                sb.append("").append(str).append("=");
            }
        }
        this.cipherText = sb.toString();
        return cipherText;
    }

    //Decrypt plain text
    public String decrypt(){
        double haxSum = getSumOfHaxIntValues();
        StringBuilder sb = new StringBuilder();
        try{
            String[] cipherTextArray = cipherText.split("=");
            for (String str : cipherTextArray) {
                try {
                    double dummy = Double.parseDouble(str);
                    sb.append("").append(Math.round((dummy * haxSum)));
                } catch (NumberFormatException e) {
                    sb.append("").append(str);
                }
            }
        }catch (Exception e){
            sb.append("");
        }
        return hexToPlainText(sb.toString());
    }
}