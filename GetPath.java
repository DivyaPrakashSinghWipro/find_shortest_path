/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getpath;

/**
 *
 * @author DI20024020
 */
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
public class GetPath {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner scan=new Scanner(System.in);
        StoreData sd=new StoreData();
        System.out.println("Enter the path of the input file");
        String path=scan.nextLine();
        sd.storeTheData(path);
        sd.start.num=1;
        sd.recur(sd.combo,sd.start);
        sd.reverse(sd.combo, sd.end);
          
    }
    
     
     
    
}
//class to store the data from the txt file into a graph datastructure.
class StoreData{
    
    
    Node combo=null;
    Node start=null;
    Node end=null;
    void storeTheData(String path){
        
        try{
        File file=new File(path);
        FileInputStream fi=new FileInputStream(file);
        char ch=' ';
        Node first=null;
        Node first2=null;
        int i=0;
        while(fi.available()>0){
            
            ch=(char)fi.read();
            if(i==0){
                first=new Node(ch,null,null,null,null);
                i++;
                first2=first;
                ch=(char)fi.read();  
            }
            else if(ch!='\n' && fi.available()>0 && ch!=' '){
                first2.right=new Node(ch,null,null,null,null);
                first2.right.left=first2;
                first2=first2.right;
                
            }
            else if(ch=='\n'){
                i=0;
                this.combo=joinTheRows(first,this.combo);
            }
            else if(fi.available()==0){
                first2.right=new Node(ch,null,null,null,null);
                first2.right.left=first2;
                first2=first2.right;
                this.combo=joinTheRows(first,this.combo);
            }
            
        }    
        }
        catch(Exception e){
           System.out.println(e.getMessage());
                    
        }
    }
    //funtion which joins the any two given graph row wise
    Node joinTheRows(Node a,Node combo){
        if(combo==null){
            return a;
        }
        else{
            Node last=combo;
            while(last.down!=null){
                last=last.down;
            }
            while(last.right!=null){
                //get start and end
                char ch=last.stat;
                char ch2=a.stat;
                if(ch=='S' || ch=='s' || ch=='E' || ch=='e' || ch2=='S' || ch2=='s' || ch2=='E' || ch2=='e' ){
                    
                    if(ch=='S' || ch=='s'){
                        start=last;
                    }
                    else if(ch2=='S' || ch2=='s'){
                        start=a;
                    }
                    else{
                        if(ch=='e'||ch=='E'){
                            end=last;
                        }
                        else{
                            end=a;
                        }
                    }
                }
                //
                last.down=a;
                a.up=last;
                last=last.right;
                a=a.right;
            }
            char ch=last.stat;
            char ch2=a.stat;
                if(ch=='S' || ch=='s' || ch=='E' || ch=='e' || ch2=='S' || ch2=='s' || ch2=='E' || ch2=='e' ){
                    
                    if(ch=='S' || ch=='s'){
                        start=last;
                    }
                    else if(ch2=='S' || ch2=='s'){
                        start=a;
                    }
                    else{
                        if(ch=='e'||ch=='E'){
                            end=last;
                        }
                        else{
                            end=a;
                        }
                    }
                }
            last.down=a;
            a.up=last;
        }
        return combo;
    }
    //prints the graph on console
    
    void printData(Node c){
        while(c.down!=null){
            
            Node gg=c;
            while(gg.right!=null){
                System.out.print(gg.stat+" ");
                gg=gg.right;
            }
            System.out.print(gg.stat);
            c=c.down;
            System.out.println();
        }
        while(c.right!=null){
                
                System.out.print(c.stat+" ");
                c=c.right;
            }
            System.out.println(c.stat);
    }
    
   
    //recursion function used to get the shortest path
    void recur(Node h,Node a){
   
       
        if(a.right!=null && a.right.stat=='E'){
            a.right.num=a.num+1; return;
        }
        else if(a.left!=null && a.left.stat=='E')
        {a.left.num=a.num+1; return;}
        else if(a.up!=null && a.up.stat=='E')
            {a.up.num=a.num+1; return;}
        else if(a.down!=null && a.down.stat=='E'){
            a.down.num=a.num+1; return;
        }
        
       if(a.right!=null && a.right.stat!='W' && a.right.stat!='S'){
           if(a.right.stat=='.'){
               a.right.stat='"';
               a.right.num=a.num+1;
           }
           if(a.right.num-a.num>1){
               a.right.num=a.num+1;
           }

       }
       if(a.left!=null && a.left.stat!='W' && a.left.stat!='S'){
           
           if(a.left.stat=='.'){
               a.left.stat='"';
               a.left.num=a.num+1;
           }
           if(a.left.num-a.num>1){
               a.left.num=a.num+1;
           }

       }
       if(a.up!=null && a.up.stat!='W' && a.up.stat!='S'){
           
           
           if(a.up.stat=='.'){
               a.up.stat='"';
               a.up.num=a.num+1;
           }
           if(a.up.num-a.num>1){
               a.up.num=a.num+1;
           }

       }
       if(a.down!=null  && a.down.stat!='W' && a.down.stat!='S'){
           
           
           if(a.down.stat=='.'){
               a.down.stat='"';
               a.down.num=a.num+1;
           }
           if(a.down.num-a.num>1){
               a.down.num=a.num+1;
           }

       }
       
       
       
       if(a.right!=null && a.right.stat=='"' && a.right.stat!='S' && a.right.num>a.num){
           
           recur(h,a.right);
           
       }
       
       if(a.left!=null && a.left.stat=='"' && a.left.stat!='S' && a.left.num>a.num){
          
           recur(h,a.left);
           
       }
       if(a.down!=null && a.down.stat=='"' && a.down.stat!='S' && a.down.num>a.num){
          
           recur(h,a.down);
           
       }
       
       if(a.up!=null && a.up.stat=='"' && a.up.stat!='S' && a.up.num>a.num){
           
           recur(h,a.up);
           
       }
       
       
       
       
       
     }
    //function to travels the graph from the end point to starting point via shortest path
    void reverse(Node h,Node ee){
        try{
        if(ee.right.stat=='S'){

            printData(h);
            System.exit(0);
        }
        }
        catch(Exception e){
            
        }
        try{
        if(ee.left.stat=='S'){

            printData(h);
            System.exit(0);
        }
        }
        catch(Exception e){
            
        }
        try{
        if(ee.up.stat=='S'){

            printData(h);
            System.exit(0);
        }
        }
        catch(Exception e){
            
        }
        try{
        if(ee.down.stat=='S'){

            printData(h);
            System.exit(0);
        }
        }
        catch(Exception e){
            
        }
        Node gg=ee;
        if(ee.right!=null && ee.num>gg.right.num && ee.right.num!=0){
            gg=ee.right;
        }
        if(ee.left!=null && ee.left.num<gg.num && ee.left.num!=0){
            gg=ee.left;
        }
        if(ee.up!=null && ee.up.num<gg.num && ee.up.num!=0){
            gg=ee.up;
        }
        if(ee.down!=null && ee.down.num<gg.num && ee.down.num!=0){
            gg=ee.down;
        }
        gg.stat='*';
        reverse(h,gg);
}
}
//Node used in graph
class Node{
    char stat;
    Node up,down,left,right;
    int num=0;
    Node(char a,Node b,Node c,Node e,Node f){
      stat=a;  
      up=b;
      down=c;
      right=f;
      left=e;
    }
    Node(){
      stat='.';  
      up=null;
      down=null;
      right=null;
      left=null;
    }
}
