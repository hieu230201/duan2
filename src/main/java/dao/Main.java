package dao;

public class Main {
    public static void main(String[] args) {
        roboPosition("TL,Go 2,Go 2,Go 2,TR,Go 2,Go 2,TR,Go 2");
    }

 public static int[] roboPosition(String run) {
        String [] arr = new String[1000];
        int k = 0;
        int a [] = new int[2];
        a[0] = 0;
        a[1] = 0;
         boolean dung = true;
         boolean ngang = true;
        for (int i = 0; i < run.length() - 1; i++) {
            String temp = String.valueOf(run.charAt(i)) + String.valueOf(run.charAt(i+1));
            if(temp.equals("TL") || temp.equals("TB") || temp.equals("TR")){
                arr[k] = temp;
                k++;
                continue;
            }
            if(temp.equals("Go")){
                i+=3;
                arr[k] = temp + " " + String.valueOf(run.charAt(i));
                k++;
            }

        }

//     for (int i = 0; i < arr.length; i++) {
//         if(arr[i].equals())
//     }
        return null;
    }
}
