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
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
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
        Queue<Node> q=new LinkedList<>();
        try{
        sd.start.num=1;
        q.add(sd.start);
        sd.recur(sd.combo,q);
       // sd.reverse(sd.combo, sd.end);
        }
        catch(StackOverflowError  e){
        if(e.getMessage()==null){
            System.out.println("No path exists between S and E");
        }
    }
        catch(NoSuchElementException ee){
             System.out.println("No path exists between S and E");
        }
          
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
    
  
    //function used to get the shortest path
    
    
    void recur(Node h,Queue q){
       Node f=(Node)q.remove();
       if(f.right!=null){
           if(f.right.stat=='E'){
               f.right.num=f.num+1;
               reverse(h,f.right);
           }
           else if(f.right.stat!='W' && f.right.stat!='S' && f.right.stat!='"' ){
               f.right.stat='"';
               f.right.num=f.num+1;
               q.add(f.right);
           }
       }
       if(f.left!=null){
           if(f.left.stat=='E'){
               f.left.num=f.num+1;
               reverse(h,f.left);
           }
           else if(f.left.stat!='W' && f.left.stat!='S' && f.left.stat!='"' ){
               f.left.stat='"';
               f.left.num=f.num+1;
               q.add(f.left);
           }
       }
       if(f.up!=null){
           if(f.up.stat=='E'){
               f.up.num=f.num+1;
               reverse(h,f.up);
           }
           else if(f.up.stat!='W' && f.up.stat!='S' && f.up.stat!='"' ){
               f.up.stat='"';
               f.up.num=f.num+1;
               q.add(f.up);
           }
       }
       if(f.down!=null){
           if(f.down.stat=='E'){
               f.down.num=f.num+1;
               reverse(h,f.down);
           }
           else if(f.down.stat!='W' && f.down.stat!='S' && f.down.stat!='"' ){
               f.down.stat='"';
               f.down.num=f.num+1;
               q.add(f.down);
           }
       }
       
       recur(h,q);
        
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
