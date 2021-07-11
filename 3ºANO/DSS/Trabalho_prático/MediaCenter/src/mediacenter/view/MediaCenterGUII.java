package mediacenter.view;

import java.awt.Color;
import java.awt.Image;
import java.util.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import mediacenter.control.MediaCenterLN;
import mediacenter.model.InfoMusica;

public class MediaCenterGUII extends javax.swing.JFrame {

    private int identificador;
    private String musica;
    private String artista;
    private String album;
    private boolean utilizador_convidado = false;
    private boolean bg_bi = true; // true= bg false=bi
    private List<InfoMusica> lista_damusica;
    private MediaCenterLN ln;
    private String email;
    private boolean restricao; // true tem parametros na pesquisa senao nao
    private String input_da_pesquisa;
    private List<Integer> correspondenciadoquemostra;
    private String Categoria_a_eliminar;
    private boolean musica_video;

    public MediaCenterGUII() {
        initComponents();
        //centrar
        this.setLocationRelativeTo(null);
        login.setLocationRelativeTo(this);
        menu_upload.setLocationRelativeTo(this);
        popup_paths_invalidos.setLocationRelativeTo(this);
        menu_categorizar.setLocationRelativeTo(this);
        butao_musica.setForeground(Color.blue);
        butao_bg.setForeground(Color.blue);
        musica_video = true;
        butao_bi.setVisible(false);
        this.ln = new MediaCenterLN();
        this.bg_bi = true;
        butao_play.setVisible(false);
        butao_categorizar.setVisible(false);
        this.lista_damusica = ln.mostraBiblioteca_geral();
        this.preenche_lista();
        //resize da lupa
        ImageIcon myimage = new ImageIcon(getClass().getResource("/mediacenter/imagens/icons/lupa.png"));
        Image img1 = myimage.getImage();
        Image img2 = img1.getScaledInstance(imagem_lupa.getWidth(), imagem_lupa.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon i = new ImageIcon(img2);
        imagem_lupa.setIcon(i);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menu_upload = new javax.swing.JDialog();
        painel_upload = new javax.swing.JPanel();
        input_conteudo = new javax.swing.JTextField();
        label_conteudo = new javax.swing.JLabel();
        label_artista = new javax.swing.JLabel();
        input_artista = new javax.swing.JTextField();
        label_album = new javax.swing.JLabel();
        input_album = new javax.swing.JTextField();
        video_checkbox = new javax.swing.JCheckBox();
        musica_checkbox = new javax.swing.JCheckBox();
        butao_escolher_imagem = new javax.swing.JButton();
        butao_escolher_path = new javax.swing.JButton();
        butao_confirmar_upload = new javax.swing.JButton();
        file_chooser_upload = new javax.swing.JFileChooser();
        imagem_chooser_upload = new javax.swing.JFileChooser();
        popup_paths_invalidos = new javax.swing.JDialog();
        painel_popup = new javax.swing.JPanel();
        msg_erro = new javax.swing.JLabel();
        menu_categorizar = new javax.swing.JDialog();
        painel_categorizar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        input_categoria = new javax.swing.JTextField();
        label_add = new javax.swing.JLabel();
        confirma_cat = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabela_categorias = new javax.swing.JTable();
        butao_eliminar = new javax.swing.JButton();
        login = new javax.swing.JDialog();
        painel_login = new javax.swing.JPanel();
        input_pw = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        input_user = new javax.swing.JTextField();
        butao_convidado = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        butao_login = new javax.swing.JButton();
        painel_principal = new javax.swing.JPanel();
        logout = new javax.swing.JButton();
        butao_upload = new javax.swing.JButton();
        butao_bi = new javax.swing.JButton();
        butao_bg = new javax.swing.JButton();
        painel_lista = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lmusica = new javax.swing.JTable();
        butao_musica = new javax.swing.JLabel();
        butao_video = new javax.swing.JLabel();
        painel_apresenta_musica = new javax.swing.JPanel();
        imagem_no_menu = new javax.swing.JLabel();
        butao_play = new javax.swing.JButton();
        texto_album = new javax.swing.JLabel();
        texto_artista = new javax.swing.JLabel();
        texto_nome = new javax.swing.JLabel();
        butao_categorizar = new javax.swing.JButton();
        painel_pesquisa = new javax.swing.JPanel();
        imagem_lupa = new javax.swing.JLabel();
        input_pesquisa = new javax.swing.JTextField();

        menu_upload.setAlwaysOnTop(true);
        menu_upload.setBackground(new java.awt.Color(255, 255, 255));
        menu_upload.setMinimumSize(new java.awt.Dimension(641, 315));
        menu_upload.setResizable(false);
        menu_upload.setSize(new java.awt.Dimension(641, 315));

        painel_upload.setBackground(new java.awt.Color(255, 255, 255));
        painel_upload.setMaximumSize(new java.awt.Dimension(641, 315));
        painel_upload.setMinimumSize(new java.awt.Dimension(641, 315));

        label_conteudo.setText("Nome do Conteúdo");

        label_artista.setText("Artista");

        label_album.setText("Album");

        video_checkbox.setText("Video");

        musica_checkbox.setText("Música");

        butao_escolher_imagem.setText("Escolher Imagem");
        butao_escolher_imagem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                butao_escolher_imagemMouseClicked(evt);
            }
        });

        butao_escolher_path.setText("Path do ficheiro");
        butao_escolher_path.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                butao_escolher_pathMouseClicked(evt);
            }
        });

        butao_confirmar_upload.setText("Confirmar");
        butao_confirmar_upload.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                butao_confirmar_uploadMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout painel_uploadLayout = new javax.swing.GroupLayout(painel_upload);
        painel_upload.setLayout(painel_uploadLayout);
        painel_uploadLayout.setHorizontalGroup(
            painel_uploadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 641, Short.MAX_VALUE)
            .addGroup(painel_uploadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(painel_uploadLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(painel_uploadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(painel_uploadLayout.createSequentialGroup()
                            .addGap(535, 535, 535)
                            .addComponent(butao_confirmar_upload))
                        .addGroup(painel_uploadLayout.createSequentialGroup()
                            .addGroup(painel_uploadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(label_artista, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(label_conteudo)
                                .addComponent(label_album, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(27, 27, 27)
                            .addGroup(painel_uploadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(input_album, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(input_conteudo, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(input_artista, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(painel_uploadLayout.createSequentialGroup()
                            .addGroup(painel_uploadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(painel_uploadLayout.createSequentialGroup()
                                    .addGap(199, 199, 199)
                                    .addComponent(butao_escolher_imagem)
                                    .addGap(18, 18, 18))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painel_uploadLayout.createSequentialGroup()
                                    .addComponent(musica_checkbox)
                                    .addGap(35, 35, 35)))
                            .addGroup(painel_uploadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(painel_uploadLayout.createSequentialGroup()
                                    .addGap(12, 12, 12)
                                    .addComponent(video_checkbox))
                                .addComponent(butao_escolher_path))))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        painel_uploadLayout.setVerticalGroup(
            painel_uploadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 315, Short.MAX_VALUE)
            .addGroup(painel_uploadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(painel_uploadLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(painel_uploadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(label_conteudo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(input_conteudo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(painel_uploadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(label_artista, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(input_artista, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(painel_uploadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(label_album, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(input_album, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(painel_uploadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(butao_escolher_imagem)
                        .addComponent(butao_escolher_path))
                    .addGap(18, 18, Short.MAX_VALUE)
                    .addGroup(painel_uploadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(painel_uploadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(video_checkbox)
                            .addComponent(musica_checkbox))
                        .addComponent(butao_confirmar_upload))
                    .addContainerGap()))
        );

        javax.swing.GroupLayout menu_uploadLayout = new javax.swing.GroupLayout(menu_upload.getContentPane());
        menu_upload.getContentPane().setLayout(menu_uploadLayout);
        menu_uploadLayout.setHorizontalGroup(
            menu_uploadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painel_upload, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        menu_uploadLayout.setVerticalGroup(
            menu_uploadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painel_upload, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        file_chooser_upload.setBackground(new java.awt.Color(255, 255, 255));

        imagem_chooser_upload.setBackground(new java.awt.Color(255, 255, 255));

        popup_paths_invalidos.setAlwaysOnTop(true);
        popup_paths_invalidos.setBackground(new java.awt.Color(255, 255, 255));
        popup_paths_invalidos.setMinimumSize(new java.awt.Dimension(724, 100));
        popup_paths_invalidos.setResizable(false);
        popup_paths_invalidos.setSize(new java.awt.Dimension(724, 100));
        popup_paths_invalidos.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                popup_paths_invalidosWindowClosed(evt);
            }
        });

        painel_popup.setBackground(new java.awt.Color(255, 255, 255));
        painel_popup.setMaximumSize(new java.awt.Dimension(724, 100));
        painel_popup.setMinimumSize(new java.awt.Dimension(724, 100));

        msg_erro.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        msg_erro.setText("texto");

        javax.swing.GroupLayout painel_popupLayout = new javax.swing.GroupLayout(painel_popup);
        painel_popup.setLayout(painel_popupLayout);
        painel_popupLayout.setHorizontalGroup(
            painel_popupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painel_popupLayout.createSequentialGroup()
                .addContainerGap(112, Short.MAX_VALUE)
                .addComponent(msg_erro, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        painel_popupLayout.setVerticalGroup(
            painel_popupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel_popupLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(msg_erro, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout popup_paths_invalidosLayout = new javax.swing.GroupLayout(popup_paths_invalidos.getContentPane());
        popup_paths_invalidos.getContentPane().setLayout(popup_paths_invalidosLayout);
        popup_paths_invalidosLayout.setHorizontalGroup(
            popup_paths_invalidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painel_popup, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        popup_paths_invalidosLayout.setVerticalGroup(
            popup_paths_invalidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painel_popup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        menu_categorizar.setAlwaysOnTop(true);
        menu_categorizar.setBackground(new java.awt.Color(255, 255, 255));
        menu_categorizar.setMinimumSize(new java.awt.Dimension(682, 304));
        menu_categorizar.setResizable(false);
        menu_categorizar.setSize(new java.awt.Dimension(682, 304));

        painel_categorizar.setBackground(new java.awt.Color(255, 255, 255));
        painel_categorizar.setMaximumSize(new java.awt.Dimension(682, 304));
        painel_categorizar.setMinimumSize(new java.awt.Dimension(682, 304));

        jLabel1.setText("Selecione o tipo que deseja categorizar");

        label_add.setText("add:");

        confirma_cat.setText("Confirmar");
        confirma_cat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                confirma_catMouseClicked(evt);
            }
        });

        tabela_categorias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Categorias"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabela_categorias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabela_categoriasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabela_categorias);

        butao_eliminar.setText("Remover");
        butao_eliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                butao_eliminarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout painel_categorizarLayout = new javax.swing.GroupLayout(painel_categorizar);
        painel_categorizar.setLayout(painel_categorizarLayout);
        painel_categorizarLayout.setHorizontalGroup(
            painel_categorizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel_categorizarLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(painel_categorizarLayout.createSequentialGroup()
                .addGroup(painel_categorizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painel_categorizarLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(label_add)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(input_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(painel_categorizarLayout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(confirma_cat)
                        .addGap(48, 48, 48)
                        .addComponent(butao_eliminar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        painel_categorizarLayout.setVerticalGroup(
            painel_categorizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel_categorizarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGroup(painel_categorizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painel_categorizarLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(painel_categorizarLayout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addGroup(painel_categorizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(input_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_add))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                        .addGroup(painel_categorizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(confirma_cat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(butao_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42))))
        );

        javax.swing.GroupLayout menu_categorizarLayout = new javax.swing.GroupLayout(menu_categorizar.getContentPane());
        menu_categorizar.getContentPane().setLayout(menu_categorizarLayout);
        menu_categorizarLayout.setHorizontalGroup(
            menu_categorizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painel_categorizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        menu_categorizarLayout.setVerticalGroup(
            menu_categorizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painel_categorizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        login.setAlwaysOnTop(true);
        login.setBackground(new java.awt.Color(255, 255, 255));
        login.setResizable(false);
        login.setSize(new java.awt.Dimension(594, 301));
        login.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                loginWindowClosed(evt);
            }
        });

        painel_login.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setText("Password:");

        jLabel4.setText("utilizador:");

        butao_convidado.setText("Entrar como Convidado");
        butao_convidado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                butao_convidadoMouseClicked(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mediacenter/imagens/icons/logo.png"))); // NOI18N
        jLabel5.setMaximumSize(new java.awt.Dimension(100, 100));

        butao_login.setText("Confirmar");
        butao_login.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                butao_loginMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout painel_loginLayout = new javax.swing.GroupLayout(painel_login);
        painel_login.setLayout(painel_loginLayout);
        painel_loginLayout.setHorizontalGroup(
            painel_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painel_loginLayout.createSequentialGroup()
                .addContainerGap(333, Short.MAX_VALUE)
                .addGroup(painel_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(butao_convidado)
                    .addGroup(painel_loginLayout.createSequentialGroup()
                        .addGroup(painel_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(0, 0, 0)
                        .addGroup(painel_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(butao_login)
                            .addGroup(painel_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(input_pw, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(input_user, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(30, 30, 30))
            .addGroup(painel_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(painel_loginLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(248, Short.MAX_VALUE)))
        );
        painel_loginLayout.setVerticalGroup(
            painel_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel_loginLayout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(painel_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(input_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(painel_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(input_pw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(butao_login)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(butao_convidado)
                .addGap(32, 32, 32))
            .addGroup(painel_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(painel_loginLayout.createSequentialGroup()
                    .addContainerGap(26, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout loginLayout = new javax.swing.GroupLayout(login.getContentPane());
        login.getContentPane().setLayout(loginLayout);
        loginLayout.setHorizontalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painel_login, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        loginLayout.setVerticalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginLayout.createSequentialGroup()
                .addComponent(painel_login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(1, 1, 1));
        setResizable(false);

        painel_principal.setBackground(new java.awt.Color(255, 255, 255));
        painel_principal.setForeground(new java.awt.Color(255, 255, 255));

        logout.setText("Logout");
        logout.setMaximumSize(new java.awt.Dimension(148, 30));
        logout.setMinimumSize(new java.awt.Dimension(148, 30));
        logout.setPreferredSize(new java.awt.Dimension(148, 30));
        logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutMouseClicked(evt);
            }
        });

        butao_upload.setText("Upload");
        butao_upload.setMaximumSize(new java.awt.Dimension(148, 30));
        butao_upload.setMinimumSize(new java.awt.Dimension(148, 30));
        butao_upload.setPreferredSize(new java.awt.Dimension(148, 30));
        butao_upload.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                butao_uploadMouseClicked(evt);
            }
        });

        butao_bi.setText("Biblioteca Individual");
        butao_bi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                butao_biMouseClicked(evt);
            }
        });

        butao_bg.setText("Biblioteca Geral");
        butao_bg.setMaximumSize(new java.awt.Dimension(148, 30));
        butao_bg.setMinimumSize(new java.awt.Dimension(148, 30));
        butao_bg.setPreferredSize(new java.awt.Dimension(148, 30));
        butao_bg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                butao_bgMouseClicked(evt);
            }
        });

        painel_lista.setBackground(new java.awt.Color(255, 255, 255));
        painel_lista.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 0, new java.awt.Color(240, 240, 240)));

        lmusica.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nome", "Artista", "Album"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        lmusica.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lmusicaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lmusica);

        butao_musica.setText("Música");
        butao_musica.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                butao_musicaMouseClicked(evt);
            }
        });

        butao_video.setText("Vídeo");
        butao_video.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                butao_videoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout painel_listaLayout = new javax.swing.GroupLayout(painel_lista);
        painel_lista.setLayout(painel_listaLayout);
        painel_listaLayout.setHorizontalGroup(
            painel_listaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel_listaLayout.createSequentialGroup()
                .addGap(151, 151, 151)
                .addComponent(butao_musica)
                .addGap(121, 121, 121)
                .addComponent(butao_video)
                .addContainerGap(232, Short.MAX_VALUE))
            .addGroup(painel_listaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 592, Short.MAX_VALUE))
        );
        painel_listaLayout.setVerticalGroup(
            painel_listaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel_listaLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(painel_listaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(butao_musica)
                    .addComponent(butao_video))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(painel_listaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painel_listaLayout.createSequentialGroup()
                    .addGap(0, 68, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        painel_apresenta_musica.setBackground(new java.awt.Color(255, 255, 255));
        painel_apresenta_musica.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 0, 0, 0, new java.awt.Color(240, 240, 240)));
        painel_apresenta_musica.setMaximumSize(new java.awt.Dimension(421, 210));
        painel_apresenta_musica.setMinimumSize(new java.awt.Dimension(421, 210));
        painel_apresenta_musica.setPreferredSize(new java.awt.Dimension(421, 210));

        butao_play.setText("Play");
        butao_play.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                butao_playMouseClicked(evt);
            }
        });

        texto_album.setMaximumSize(new java.awt.Dimension(120, 32));
        texto_album.setMinimumSize(new java.awt.Dimension(120, 32));
        texto_album.setPreferredSize(new java.awt.Dimension(120, 32));

        texto_artista.setFont(new java.awt.Font("Waree", 3, 15)); // NOI18N
        texto_artista.setMaximumSize(new java.awt.Dimension(120, 32));
        texto_artista.setMinimumSize(new java.awt.Dimension(120, 32));
        texto_artista.setPreferredSize(new java.awt.Dimension(120, 32));

        texto_nome.setMaximumSize(new java.awt.Dimension(120, 32));
        texto_nome.setMinimumSize(new java.awt.Dimension(120, 32));
        texto_nome.setPreferredSize(new java.awt.Dimension(120, 32));

        butao_categorizar.setText("Categorias");
        butao_categorizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                butao_categorizarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout painel_apresenta_musicaLayout = new javax.swing.GroupLayout(painel_apresenta_musica);
        painel_apresenta_musica.setLayout(painel_apresenta_musicaLayout);
        painel_apresenta_musicaLayout.setHorizontalGroup(
            painel_apresenta_musicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel_apresenta_musicaLayout.createSequentialGroup()
                .addComponent(imagem_no_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(painel_apresenta_musicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painel_apresenta_musicaLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(butao_play, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(butao_categorizar))
                    .addGroup(painel_apresenta_musicaLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(painel_apresenta_musicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(texto_nome, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(texto_artista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(texto_album, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painel_apresenta_musicaLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {texto_album, texto_artista, texto_nome});

        painel_apresenta_musicaLayout.setVerticalGroup(
            painel_apresenta_musicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel_apresenta_musicaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(texto_artista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(texto_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(texto_album, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painel_apresenta_musicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(butao_categorizar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(butao_play, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(painel_apresenta_musicaLayout.createSequentialGroup()
                .addComponent(imagem_no_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        painel_apresenta_musicaLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {texto_album, texto_artista, texto_nome});

        painel_pesquisa.setBackground(new java.awt.Color(255, 255, 255));
        painel_pesquisa.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(240, 240, 240)));

        imagem_lupa.setMaximumSize(new java.awt.Dimension(50, 90));
        imagem_lupa.setPreferredSize(new java.awt.Dimension(50, 50));
        imagem_lupa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imagem_lupaMouseClicked(evt);
            }
        });

        input_pesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                input_pesquisaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout painel_pesquisaLayout = new javax.swing.GroupLayout(painel_pesquisa);
        painel_pesquisa.setLayout(painel_pesquisaLayout);
        painel_pesquisaLayout.setHorizontalGroup(
            painel_pesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel_pesquisaLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(imagem_lupa, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(input_pesquisa)
                .addContainerGap())
        );
        painel_pesquisaLayout.setVerticalGroup(
            painel_pesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painel_pesquisaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imagem_lupa, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painel_pesquisaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(input_pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout painel_principalLayout = new javax.swing.GroupLayout(painel_principal);
        painel_principal.setLayout(painel_principalLayout);
        painel_principalLayout.setHorizontalGroup(
            painel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel_principalLayout.createSequentialGroup()
                .addGroup(painel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(painel_apresenta_musica, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(painel_principalLayout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addGroup(painel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(logout, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, painel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(butao_bi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(butao_bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(butao_upload, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(painel_pesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painel_lista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        painel_principalLayout.setVerticalGroup(
            painel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel_principalLayout.createSequentialGroup()
                .addComponent(painel_pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 24, Short.MAX_VALUE)
                .addComponent(butao_bg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(butao_bi)
                .addGap(18, 18, 18)
                .addComponent(butao_upload, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(logout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(painel_apresenta_musica, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(painel_lista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painel_principal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painel_principal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lmusicaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lmusicaMouseClicked
        int index = lmusica.getSelectedRow();
        TableModel model = lmusica.getModel();
        butao_play.setVisible(true);
        butao_categorizar.setVisible(true);
        musica = model.getValueAt(index, 0).toString();
        artista = model.getValueAt(index, 1).toString();
        album = model.getValueAt(index, 2).toString();
        index = correspondenciadoquemostra.get(index);
        identificador = lista_damusica.get(index).getIdentificador();
        ImageIcon myimage = new ImageIcon(lista_damusica.get(index).getImagem());
        Image img1 = myimage.getImage();
        Image img2 = img1.getScaledInstance(imagem_no_menu.getWidth(), imagem_no_menu.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon i = new ImageIcon(img2);
        imagem_no_menu.setIcon(i);
        texto_nome.setText(musica);
        texto_artista.setText(artista);
        texto_album.setText(album);
        texto_nome.setVisible(true);
        texto_artista.setVisible(true);
        texto_album.setVisible(true);
        if (evt.getClickCount() == 2) {
            ln.reproduzirConteudo(identificador);
        }
    }//GEN-LAST:event_lmusicaMouseClicked

    private void preenche_lista_categorias() {
        DefaultTableModel model = (DefaultTableModel) tabela_categorias.getModel();
        model.setRowCount(0);
        Map<String, List<String>> cat = ln.getConteudoCategoria(identificador);
        if (bg_bi) {
            int i = 0;
            Collection<List<String>> valores = cat.values();
            List<String> res = new ArrayList<>();
            for (List<String> x : valores) {
                for (String l : x) {
                    res.add(l);
                }
            }
            if (res != null) {
                model.setRowCount(res.size());
                for (String im : res) {
                    model.setValueAt(im, i, 0);
                    i++;
                }
            }
        } else {
            int i = 0;
            List<String> res = cat.get(email);
            if (res != null) {
                model.setRowCount(res.size());
                for (String im : res) {
                    model.setValueAt(im, i, 0);
                    i++;
                }
            }
        }
    }

    private void preenche_lista() {
        butao_play.setVisible(false);
        butao_categorizar.setVisible(false);
        imagem_no_menu.setIcon(new ImageIcon());
        DefaultTableModel model = (DefaultTableModel) lmusica.getModel();
        model.setRowCount(0);
        if (restricao == false) {
            int i = 0;
            List<InfoMusica> aux = new ArrayList<>();
            correspondenciadoquemostra = new ArrayList<>();
            int ij = 0;
            for (InfoMusica im : lista_damusica) {
                if ((musica_video == true && im.getTipo().equals("musica"))
                        || (musica_video == false && im.getTipo().equals("video"))) {
                    aux.add(im);
                    correspondenciadoquemostra.add(ij);
                }
                ij++;
            }
            model.setRowCount(aux.size());
            for (InfoMusica im : aux) {
                model.setValueAt(im.getMusica(), i, 0);
                model.setValueAt(im.getArtista(), i, 1);
                model.setValueAt(im.getAlbum(), i, 2);
                i++;
            }
        } else {
            correspondenciadoquemostra = new ArrayList<>();
            int i = 0;
            for (InfoMusica im : lista_damusica) {
                if (((musica_video == true && im.getTipo().equals("musica"))
                        || (musica_video == false && im.getTipo().equals("video")))
                        && ((im.getMusica().toLowerCase().contains((input_da_pesquisa.toLowerCase())))
                        || (im.getArtista().toLowerCase().contains((input_da_pesquisa.toLowerCase())))
                        || (im.getAlbum().toLowerCase().contains((input_da_pesquisa.toLowerCase()))))) {
                    correspondenciadoquemostra.add(i);
                }
                i++;
            }
            model.setRowCount(correspondenciadoquemostra.size());
            for (int j = 0; j < correspondenciadoquemostra.size(); j++) {
                model.setValueAt(lista_damusica.get(correspondenciadoquemostra.get(j)).getMusica(), j, 0);
                model.setValueAt(lista_damusica.get(correspondenciadoquemostra.get(j)).getArtista(), j, 1);
                model.setValueAt(lista_damusica.get(correspondenciadoquemostra.get(j)).getAlbum(), j, 2);
            }
        }
    }


    private void butao_bgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_butao_bgMouseClicked
        restricao = false;
        butao_bg.setForeground(Color.blue);
        butao_bi.setForeground(Color.black);
        bg_bi = true;
        this.lista_damusica = ln.mostraBiblioteca_geral();
        this.preenche_lista();
        input_pesquisa.setText("");
        texto_nome.setText("");
        texto_artista.setText("");
        texto_album.setText("");
    }//GEN-LAST:event_butao_bgMouseClicked

    private void butao_biMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_butao_biMouseClicked
        restricao = false;
        butao_bi.setForeground(Color.blue);
        butao_bg.setForeground(Color.black);
        bg_bi = false;
        this.lista_damusica = ln.mostraBiblioteca_individual(email);
        this.preenche_lista();
        input_pesquisa.setText("");
        texto_nome.setText("");
        texto_artista.setText("");
        texto_album.setText("");
    }//GEN-LAST:event_butao_biMouseClicked

    private void butao_playMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_butao_playMouseClicked
        ln.reproduzirConteudo(identificador);
    }//GEN-LAST:event_butao_playMouseClicked

    private void butao_uploadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_butao_uploadMouseClicked
        input_conteudo.setText("");
        input_artista.setText("");
        input_album.setText("");
        musica_checkbox.setSelected(true);
        video_checkbox.setSelected(false);
        imagem_chooser_upload.cancelSelection();
        file_chooser_upload.cancelSelection();
        menu_upload.setVisible(true);
    }//GEN-LAST:event_butao_uploadMouseClicked

    private void butao_confirmar_uploadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_butao_confirmar_uploadMouseClicked

        FileNameExtensionFilter filterimagens = new FileNameExtensionFilter("imagem files", "png", "gif", "jpeg");
        FileNameExtensionFilter filterconteudo = new FileNameExtensionFilter("conteudo files", "mp3", "mp4");
        imagem_chooser_upload.setFileFilter(filterimagens);
        file_chooser_upload.setFileFilter(filterconteudo);
        String nome_conteudo = input_conteudo.getText();
        String artista = input_artista.getText();
        String album = input_album.getText();
        if (video_checkbox.isSelected() != musica_checkbox.isSelected()) {
            if (false == nome_conteudo.equals("") && false == artista.equals("") && false == album.equals("")) {
                String tipo;
                String imagem = "";
                if (imagem_chooser_upload.getSelectedFile() != null) {
                    imagem = imagem_chooser_upload.getSelectedFile().getAbsolutePath();
                    String local = "";
                    if (file_chooser_upload.getSelectedFile() != null) {
                        local = file_chooser_upload.getSelectedFile().getAbsolutePath();
                        System.out.println("path local é" + local + "");
                        System.out.println("path da imagemé" + imagem + "");
                        if (video_checkbox.isSelected()) {
                            tipo = "video";
                        } else {
                            tipo = "musica";
                        }
                        ln.criaConteudo(email, nome_conteudo, local, artista, album, tipo, imagem);
                        menu_upload.setVisible(false);
                        this.preenche_lista();
                    } else {
                        msg_erro.setText("Não inseriu o ficheiro ou inseriu um inválido, tente outra-vez");
                        popup_paths_invalidos.setVisible(true);
                    }
                } else {
                    msg_erro.setText("Não inseriu a imagem ou inseriu uma inválida, tente outra-vez");
                    popup_paths_invalidos.setVisible(true);
                }
            } else {
                msg_erro.setText("Insira todos os campos");
                popup_paths_invalidos.setVisible(true);
            }
        } else {
            msg_erro.setText("Selecione 1 e apenas uma checkbox");
            popup_paths_invalidos.setVisible(true);
        }
    }//GEN-LAST:event_butao_confirmar_uploadMouseClicked

    private void butao_escolher_pathMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_butao_escolher_pathMouseClicked
        file_chooser_upload.showSaveDialog(null);
    }//GEN-LAST:event_butao_escolher_pathMouseClicked

    private void butao_escolher_imagemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_butao_escolher_imagemMouseClicked
        imagem_chooser_upload.showSaveDialog(null);
    }//GEN-LAST:event_butao_escolher_imagemMouseClicked

    private void butao_categorizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_butao_categorizarMouseClicked

        if (bg_bi) {
            butao_eliminar.setVisible(false);
        } else {
            butao_eliminar.setVisible(true);
        }
        if (utilizador_convidado == false) {
            confirma_cat.setVisible(false);
            input_categoria.setVisible(false);
            butao_eliminar.setVisible(false);
        } else {
            confirma_cat.setVisible(true);
            input_categoria.setText("");
            input_categoria.setVisible(true);
        }
        preenche_lista_categorias();
        menu_categorizar.setVisible(true);
    }//GEN-LAST:event_butao_categorizarMouseClicked

    private void confirma_catMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirma_catMouseClicked

        String outro = input_categoria.getText();
        if (outro.equals("") == false) {
            ln.addCategoria(outro, identificador, email);
            preenche_lista_categorias();
        }
    }//GEN-LAST:event_confirma_catMouseClicked

    private void butao_convidadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_butao_convidadoMouseClicked
        restricao = false;
        input_pesquisa.setText("");
        utilizador_convidado = false;
        login.setVisible(false);
        utilizador_convidado = false;
        butao_upload.setVisible(false);
        butao_bi.setVisible(false);
        butao_bi.setForeground(Color.black);
        butao_bg.setForeground(Color.blue);
        this.lista_damusica = ln.mostraBiblioteca_geral();
        preenche_lista();
        this.setVisible(true);
        bg_bi = true;
        butao_eliminar.setVisible(false);
        label_add.setVisible(false);
        texto_nome.setVisible(false);
        texto_artista.setVisible(false);
        texto_album.setVisible(false);
    }//GEN-LAST:event_butao_convidadoMouseClicked

    private void butao_loginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_butao_loginMouseClicked
        String input_email = input_user.getText();
        String input_password = input_pw.getText();
        if (ln.login(input_email, input_password) >= 2) {
            restricao = false;
            input_pesquisa.setText("");
            login.setVisible(false);
            email = input_email;
            utilizador_convidado = true;
            butao_bi.setVisible(true);
            butao_bi.setForeground(Color.black);
            butao_bg.setForeground(Color.blue);
            butao_upload.setVisible(true);
            bg_bi = true;
            this.lista_damusica = ln.mostraBiblioteca_geral();
            preenche_lista();
            this.setVisible(true);
            texto_nome.setVisible(false);
            texto_artista.setVisible(false);
            texto_album.setVisible(false);
        } else {
            msg_erro.setText("Combinação errada");
            popup_paths_invalidos.setVisible(true);
        }
    }//GEN-LAST:event_butao_loginMouseClicked

    private void logoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMouseClicked
        this.setVisible(false);
        musica_video = true;
        butao_musica.setForeground(Color.blue);
        butao_video.setForeground(Color.black);
        menu_upload.setVisible(false);
        popup_paths_invalidos.setVisible(false);
        menu_categorizar.setVisible(false);
        login.setVisible(true);
    }//GEN-LAST:event_logoutMouseClicked

    private void popup_paths_invalidosWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_popup_paths_invalidosWindowClosed
        if (false == this.isVisible()) {
            menu_upload.setVisible(false);
            popup_paths_invalidos.setVisible(false);
            menu_categorizar.setVisible(false);
            login.setVisible(true);
        }
    }//GEN-LAST:event_popup_paths_invalidosWindowClosed

    private void loginWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_loginWindowClosed
        System.exit(0);
    }//GEN-LAST:event_loginWindowClosed

    private void imagem_lupaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imagem_lupaMouseClicked
        if (bg_bi) {
            this.lista_damusica = ln.mostraBiblioteca_geral();
        } else {
            this.lista_damusica = ln.mostraBiblioteca_individual(email);
        }
        pesquisar();
    }//GEN-LAST:event_imagem_lupaMouseClicked

    private void pesquisar() {
        //clean parametros
        texto_nome.setVisible(false);
        texto_artista.setVisible(false);
        texto_album.setVisible(false);
        imagem_no_menu.setIcon(new ImageIcon());
        input_da_pesquisa = input_pesquisa.getText();
        if (input_da_pesquisa.equals("")) {
            restricao = false;
        } else {
            restricao = true;
        }
        preenche_lista();
    }

    private void input_pesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_input_pesquisaKeyPressed
        // if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        pesquisar();
    }//GEN-LAST:event_input_pesquisaKeyPressed

    private void butao_eliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_butao_eliminarMouseClicked
        if (Categoria_a_eliminar != null) {
            System.out.println("vou eliminar:" + Categoria_a_eliminar);
            ln.removeCategoria(Categoria_a_eliminar, email, identificador);
            preenche_lista_categorias();
            Categoria_a_eliminar = null;
        }
    }//GEN-LAST:event_butao_eliminarMouseClicked

    private void tabela_categoriasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabela_categoriasMouseClicked

        int index = tabela_categorias.getSelectedRow();
        TableModel model = tabela_categorias.getModel();
        Categoria_a_eliminar = model.getValueAt(index, 0).toString();
    }//GEN-LAST:event_tabela_categoriasMouseClicked

    private void butao_musicaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_butao_musicaMouseClicked
        musica_video = true;
        butao_video.setForeground(Color.black);
        butao_musica.setForeground(Color.blue);
        restricao = false;
        input_pesquisa.setText("");
        texto_nome.setText("");
        texto_artista.setText("");
        texto_album.setText("");
        preenche_lista();
    }//GEN-LAST:event_butao_musicaMouseClicked

    private void butao_videoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_butao_videoMouseClicked
        musica_video = false;
        butao_musica.setForeground(Color.black);
        butao_video.setForeground(Color.blue);
        restricao = false;
        input_pesquisa.setText("");
        texto_nome.setText("");
        texto_artista.setText("");
        texto_album.setText("");
        preenche_lista();
    }//GEN-LAST:event_butao_videoMouseClicked

    public void starApp() {
        login.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        login.setVisible(true);
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MediaCenterGUII.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MediaCenterGUII.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MediaCenterGUII.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MediaCenterGUII.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MediaCenterGUII().starApp();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butao_bg;
    private javax.swing.JButton butao_bi;
    private javax.swing.JButton butao_categorizar;
    private javax.swing.JButton butao_confirmar_upload;
    private javax.swing.JButton butao_convidado;
    private javax.swing.JButton butao_eliminar;
    private javax.swing.JButton butao_escolher_imagem;
    private javax.swing.JButton butao_escolher_path;
    private javax.swing.JButton butao_login;
    private javax.swing.JLabel butao_musica;
    private javax.swing.JButton butao_play;
    private javax.swing.JButton butao_upload;
    private javax.swing.JLabel butao_video;
    private javax.swing.JButton confirma_cat;
    private javax.swing.JFileChooser file_chooser_upload;
    private javax.swing.JFileChooser imagem_chooser_upload;
    private javax.swing.JLabel imagem_lupa;
    private javax.swing.JLabel imagem_no_menu;
    private javax.swing.JTextField input_album;
    private javax.swing.JTextField input_artista;
    private javax.swing.JTextField input_categoria;
    private javax.swing.JTextField input_conteudo;
    private javax.swing.JTextField input_pesquisa;
    private javax.swing.JPasswordField input_pw;
    private javax.swing.JTextField input_user;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel label_add;
    private javax.swing.JLabel label_album;
    private javax.swing.JLabel label_artista;
    private javax.swing.JLabel label_conteudo;
    private javax.swing.JTable lmusica;
    private javax.swing.JDialog login;
    private javax.swing.JButton logout;
    private javax.swing.JDialog menu_categorizar;
    private javax.swing.JDialog menu_upload;
    private javax.swing.JLabel msg_erro;
    private javax.swing.JCheckBox musica_checkbox;
    private javax.swing.JPanel painel_apresenta_musica;
    private javax.swing.JPanel painel_categorizar;
    private javax.swing.JPanel painel_lista;
    private javax.swing.JPanel painel_login;
    private javax.swing.JPanel painel_pesquisa;
    private javax.swing.JPanel painel_popup;
    private javax.swing.JPanel painel_principal;
    private javax.swing.JPanel painel_upload;
    private javax.swing.JDialog popup_paths_invalidos;
    private javax.swing.JTable tabela_categorias;
    private javax.swing.JLabel texto_album;
    private javax.swing.JLabel texto_artista;
    private javax.swing.JLabel texto_nome;
    private javax.swing.JCheckBox video_checkbox;
    // End of variables declaration//GEN-END:variables
}