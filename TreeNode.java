package com.company;

import java.util.ArrayList;

public class TreeNode {

    private String name;
    private TreeNode firstChild;
    private TreeNode nextSibling;

    TreeNode (String n, TreeNode d, TreeNode r) {
        name = n;
        firstChild = d;
        nextSibling = r;
        // TODO!!! Your constructor here
    }

    public static TreeNode parsePrefix (String s) {
        ArrayList<String> tokens = getTokens(s);
        if (tokens.size() == 0) throw new RuntimeException("Tühi sisend");
        String name = tokens.get(0);
        if ("(".equals(name) || ")".equals(name) || ",".equals(name))
            throw new RuntimeException("Vigane esimene sümbol sisendis " + s);
        if (tokens.size() == 1)
            return new TreeNode(name, null, null);
        if (!"(".equals(tokens.get(1)))
            throw new RuntimeException("Vigane sisend, juure järel ei ole sulgu: " + s);
        TreeNode root = new TreeNode(name, null, null);
        addChildren(root, tokens, 2);
        return root;  // TODO!!! return the root
    }

    private static int addChildren(TreeNode parent, ArrayList<String> tokens, int start){
        String token = tokens.get(start);
        if ("(".equals(token))
            throw new RuntimeException("Vigane topeltsulg " + (start+1) + " elemendi juures.");
        if (")".equals(token))
            throw new RuntimeException("Vigane tühisulg " + (start+1) + " elemendi juures.");
        if (",".equals(token))
            throw new RuntimeException("Vigane koma sulu järel " + (start+1) + " elemendi juures.");
        TreeNode child = new TreeNode(token, null, null);
        parent.firstChild = child;
        int next = start + 1;
        while (true){
            if (tokens.size() <= next) throw new RuntimeException("Ootamatu lõpp " + (start+1) + " elemendi juures");
            token = tokens.get(next);
            if ("(".equals(token)) {
                next = addChildren(child, tokens, next+1);
            }
            else if (")".equals(token)){
                return next+1;
            }
            else if (",".equals(token)){
                next = next+1;
                if (tokens.size() <= next) throw new RuntimeException("Ootamatu lõpp " + (start+1) + " elemendi juures");
                token = tokens.get(next);
                if ("(".equals(token) || ")".equals(token) || ",".equals(token))
                    throw new RuntimeException("Vigane sümbol koma järel: " + token);
                TreeNode nextChild = new TreeNode(token, null, null);
                child.nextSibling = nextChild;
                child = nextChild;
                next = next + 1;
            }
            else throw new RuntimeException("Kaks nime järjest " + token);
        }
    }

    private static ArrayList<String> getTokens(String s){
        ArrayList<String> tokens = new ArrayList<String>();
        String name="";
        for (int i = 0; i < s.length(); i++)
        {
            String ch=""+s.charAt(i);
            if ("(".equals(ch) || ")".equals(ch) || ",".equals(ch)){
                if (!"".equals(name)) {
                    tokens.add(name);
                    name="";
                }
                tokens.add(ch);
            }
            else if (" ".equals(ch) || "\t".equals(ch)){
                if (!"".equals(name)) {
                    tokens.add(name);
                    name="";
                }
            }
            else {
                name= name+ch;
            }
        }
        if (!"".equals(name)) {
            tokens.add(name);
        }
        return tokens;
    }

    public String rightParentheticRepresentation() {
        StringBuffer b = new StringBuffer(name);

        if (firstChild != null) {
            b.insert(0, ')');
            StringBuffer sb = new StringBuffer();
            sb.append(firstChild.rightParentheticRepresentation());
            TreeNode sibling = firstChild.nextSibling;
            while (sibling != null){
                sb.append(',');
                sb.append(sibling.rightParentheticRepresentation());
                sibling = sibling.nextSibling;
            }
            b.insert(0, sb.toString());
            b.insert(0, '(');
        }
        // TODO!!! create the result in buffer b
        return b.toString();
    }



    public static void main (String[] param) {
        String s = "A A";
        TreeNode t = TreeNode.parsePrefix (s);
        String v = t.rightParentheticRepresentation();
        System.out.println (s + " ==> " + v); // A(B1,C) ==> (B1,C)A
    }
}

