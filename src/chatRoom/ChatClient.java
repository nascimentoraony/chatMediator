package chatRoom;
import java.io.*;
import java.util.*;
import java.net.*;
import javax.swing.*;

import chatRoom.ChatClient.MessagesThread;

import java.awt.*;
import java.awt.event.*;

import static java.lang.System.out;

public class  ChatClient extends JFrame implements ActionListener {
    
    JTextArea  taMsgs;
    JTextField tfInput;
    JButton btnSend,btnExit;
    Mediator mediator = new Mediator();
    
    
    public ChatClient(String usuNome,String servername) throws Exception {
        super(usuNome);  // Define Nome na Janela.
        mediator.usuario(usuNome, servername);
        new MessagesThread().start();  // Cria a thread para "ouvir" as mensagens     
        buildInterface();
        
    }
    
    public void buildInterface() {
        btnSend = new JButton("Send");
        btnExit = new JButton("Exit");
        taMsgs = new JTextArea();
        taMsgs.setRows(10);
        taMsgs.setColumns(50);
        taMsgs.setEditable(false);
        tfInput  = new JTextField(50);
        JScrollPane sp = new JScrollPane(taMsgs, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(sp,"Center");
        JPanel bp = new JPanel( new FlowLayout());
        bp.add(tfInput);
        bp.add(btnSend);
        bp.add(btnExit);
        add(bp,"South");
        btnSend.addActionListener(this);
        btnExit.addActionListener(this);
        setSize(500,300);
        setVisible(true);
        pack();
    }
    
    public void actionPerformed(ActionEvent evt) {
        if ( evt.getSource() == btnExit ) {
            mediator.pw.println("end");  // Envia mensagem para o Server sobre o fim da sessão de cliente
            System.exit(0);
        } else {
            // Envia Mensagem para o Servidor
            mediator.pw.println(tfInput.getText());
        }
    }
    
    public static void main(String ... args) {
    
        String nome = JOptionPane.showInputDialog(null,"Informe seu nome :", "Nome",
             JOptionPane.PLAIN_MESSAGE);
        String servername = "localhost";  
        try {
            new ChatClient( nome ,servername);
        } catch(Exception ex) {
            out.println( "Erro --> " + ex.getMessage());
        }
        
    }
    
    // Classe interna para a Thread de Mensagens
    class  MessagesThread extends Thread {
        public void run() {
            String line;
            try {
                while(true) {
                    line = mediator.br.readLine();
                    taMsgs.append(line + "\n");
                } 
            } catch(Exception ex) {}
        }
    }
} 