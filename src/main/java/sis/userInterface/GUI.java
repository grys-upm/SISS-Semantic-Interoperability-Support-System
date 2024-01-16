/* Copyright 2018-2021 Universidad Politécnica de Madrid (UPM).
 *
 * Authors:
 *    Mario San Emeterio de la Parte
 *    Vicente Hernández Díaz
 *    José-Fernan Martínez Ortega
 *    Néstor Lucas Martínez
 *
 * This software is distributed under a dual-license scheme:
 *
 * - For academic uses: Licensed under GNU Affero General Public License as
 *                      published by the Free Software Foundation, either
 *                      version 3 of the License, or (at your option) any
 *                      later version.
 *
 * - For any other use: Licensed under the Apache License, Version 2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * You can get a copy of the license terms in licenses/LICENSE.
 *
 */

package sis.userInterface;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import sis.rest.RestInterface;
import sis.translator.Mapping;

public class GUI {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {

				try {
					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						if ("Windows".equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							break;
						}
					}
				} catch (Exception e) {
					// If Nimbus is not available, you can set the GUI to another look and feel.
				}
				JFrame.setDefaultLookAndFeelDecorated(true);

				JFrame frame = new JFrame("Semantic Interoperability Support System (SIS)");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(1200, 800);
				Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\mario\\eclipse-workspaceWeb\\SIS-SemanticInteroperability\\src\\main\\resources\\GUI\\GRyS-Watermark-1.png");    
				frame.setIconImage(icon);    

				// Cargar la imagen de fondo
				BufferedImage backgroundImage = ImageIO.read(new File("C:\\Users\\mario\\eclipse-workspaceWeb\\SIS-SemanticInteroperability\\src\\main\\resources\\GUI\\Background2.png"));
				BufferedImage filterImage = createFilterImage(backgroundImage.getWidth(), backgroundImage.getHeight(), 0.5f);
				BufferedImage filterImageBackground = createFilterImage(backgroundImage.getWidth(), backgroundImage.getHeight(), 0.3f);
				JPanel backgroundPanel = new JPanel() {
					@Override
					protected void paintComponent(Graphics g) {
						super.paintComponent(g);
						g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
						g.drawImage(filterImageBackground, 0, 0, getWidth(), getHeight(), this);
					}
				};
				backgroundPanel.setLayout(new BorderLayout());

				JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
				topPanel.setOpaque(false);
				JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
				centerPanel.setOpaque(false);
				JPanel midPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
				midPanel.setOpaque(false);
				JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
				bottomPanel.setOpaque(false);

				Font customFontJsonResponse = new Font("Arial", Font.PLAIN, 12);
				Font customFont = new Font("Arial", Font.PLAIN, 14);
				Font customFont2 = new Font("Arial", Font.PLAIN, 18);
				Font customFont3 = new Font("Arial", Font.ITALIC, 18);


				JTextArea textArea1Title = new JTextArea("Source Data Model"); 
				textArea1Title.setOpaque(false); textArea1Title.setForeground(Color.WHITE); textArea1Title.setFont(customFont2); textArea1Title.setEditable(false);
				JTextArea textArea2Title = new JTextArea("Target Data Model"); 
				textArea2Title.setOpaque(false); textArea2Title.setForeground(Color.WHITE); textArea2Title.setFont(customFont2); textArea2Title.setEditable(false);
				JTextArea textArea1 = new JTextArea(12,35); textArea1.setForeground(Color.WHITE);
				JTextArea textArea2 = new JTextArea(12,35); textArea2.setForeground(Color.WHITE);
				JTextArea textArea3 = new JTextArea(22,75); textArea3.setForeground(Color.WHITE);
				JTextArea textArea4 = new JTextArea(22,25); textArea4.setForeground(Color.WHITE);
				textArea3.setText("Mapping bewteen source and target data models");
				textArea3.setEditable(false);
				textArea4.setText("Information from Weather conditions");
				textArea4.setEditable(false);
				textArea1.append(DefaultInfo.JSONINPUT);
				textArea2.append(DefaultInfo.JSONOUTPUT);




				textArea1.setOpaque(false); textArea1.setFont(customFont);
				textArea2.setOpaque(false); textArea2.setFont(customFont);
				textArea3.setOpaque(false); textArea3.setFont(customFont);
				textArea4.setOpaque(false); textArea4.setFont(customFont);


				JScrollPane paneText1 = new JScrollPane(textArea1) {
					@Override
					protected void paintComponent(Graphics g) {
						super.paintComponent(g);
						Graphics2D g2d = (Graphics2D) g.create();

						// Dibuja la imagen del filtro
						g2d.drawImage(filterImage, 0, 0, getWidth(), getHeight(), this);

						g2d.dispose();
					}
				};
				paneText1.getViewport().setOpaque(false); 
				paneText1.setOpaque(false);

				JScrollPane paneText2 = new JScrollPane(textArea2) {
					@Override
					protected void paintComponent(Graphics g) {
						super.paintComponent(g);
						Graphics2D g2d = (Graphics2D) g.create();

						// Dibuja la imagen del filtro
						g2d.drawImage(filterImage, 0, 0, getWidth(), getHeight(), this);

						g2d.dispose();
					}
				};
				paneText2.getViewport().setOpaque(false); 
				paneText2.setOpaque(false);

				JScrollPane paneText3 = new JScrollPane(textArea3) {
					@Override
					protected void paintComponent(Graphics g) {
						super.paintComponent(g);
						Graphics2D g2d = (Graphics2D) g.create();

						// Dibuja la imagen del filtro
						g2d.drawImage(filterImage, 0, 0, getWidth(), getHeight(), this);

						g2d.dispose();
					}
				};
				paneText3.getViewport().setOpaque(false); 
				paneText3.setOpaque(false);

				JScrollPane paneOpenApiInfo = new JScrollPane(textArea4) {
					@Override
					protected void paintComponent(Graphics g) {
						super.paintComponent(g);
						Graphics2D g2d = (Graphics2D) g.create();

						// Dibuja la imagen del filtro
						g2d.drawImage(filterImage, 0, 0, getWidth(), getHeight(), this);

						g2d.dispose();
					}
				};
				paneOpenApiInfo.getViewport().setOpaque(false); 
				paneOpenApiInfo.setOpaque(false);


				// Traduction Button
				JButton translateButton = new JButton("Get Mapping");
				translateButton.setFont(customFont);
				translateButton.setBackground(Color.LIGHT_GRAY);
				translateButton.setSize(84, 84);
				translateButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						String sourceJSON = textArea1.getText(); String targetJSON = textArea2.getText();
						String[] response = RestInterface.generateMap(sourceJSON, targetJSON);
						JTable mappings = null; JTable prefixes = null;
						mappings = MappingAdapter.fromRESTMappingToGUIMapping(response[0]);
						prefixes = MappingAdapter.getPrefixes();

						if(mappings!=null && prefixes != null) { 
							Box result = Box.createVerticalBox();	
							Box completeResultInfo = Box.createVerticalBox();

							if(!response[1].isBlank()) {

								textArea4.setText(response[1]);
								textArea4.setFont(customFontJsonResponse);
							}

							JScrollPane tablePrefixes = new JScrollPane(prefixes);
							tablePrefixes.getViewport().setOpaque(false);
							tablePrefixes.setOpaque(false);
							tablePrefixes.setMaximumSize(new Dimension(paneText3.getWidth(),50));
							JScrollPane tableResults = new JScrollPane(mappings);
							tableResults.getViewport().setOpaque(false);
							tableResults.setOpaque(false);
							tableResults.setMaximumSize(new Dimension(paneText3.getWidth(),paneText3.getHeight()));
							JTextArea resultQuery = new JTextArea("SELECT * WHERE {?subject ?predicate ?object}");
							resultQuery.setOpaque(false); resultQuery.setFont(customFont3); resultQuery.setForeground(Color.WHITE); resultQuery.setEditable(false);
							resultQuery.setMaximumSize(new Dimension(500,50));

							
							JScrollPane tableRemainingPropertiesSource = new JScrollPane(MappingAdapter.getRemainingProperties(Mapping.getSourceMap()));
							tableRemainingPropertiesSource.getViewport().setOpaque(false);
							tableRemainingPropertiesSource.setOpaque(false);
							
							JScrollPane tableRemainingPropertiesTarget = new JScrollPane(MappingAdapter.getRemainingProperties(Mapping.getTargetMap()));
							tableRemainingPropertiesTarget.getViewport().setOpaque(false);
							tableRemainingPropertiesTarget.setOpaque(false);
							
							JPanel remainingProperties = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
							remainingProperties.setOpaque(false);
							
							remainingProperties.add(tableRemainingPropertiesSource);
							remainingProperties.add(tableRemainingPropertiesTarget);
							
							result.add(resultQuery);
							result.add(tablePrefixes);
							result.add(tableResults);
//							completeResultInfo.add(result);
//							completeResultInfo.add(remainingProperties);
							result.add(remainingProperties);
							paneText3.setViewportView(result);
						}
						else {textArea3.setText(response[0].replace(",", "\n")); paneText3.setViewportView(textArea3);}

					}
				});

				Box source = Box.createVerticalBox();
				source.add(textArea1Title);
				source.add(paneText1);

				Box target = Box.createVerticalBox();
				target.add(textArea2Title);
				target.add(paneText2);

				centerPanel.add(source);
				centerPanel.add(target);
				midPanel.add(translateButton);
				bottomPanel.add(paneText3);
				bottomPanel.add(paneOpenApiInfo);

				centerPanel.setBackground(new Color(0, 0, 0, 192));
				bottomPanel.setBackground(new Color(0, 0, 0, 192));

				backgroundPanel.add(centerPanel, BorderLayout.NORTH);
				backgroundPanel.add(midPanel, BorderLayout.CENTER);
				backgroundPanel.add(bottomPanel, BorderLayout.SOUTH);


				frame.setContentPane(backgroundPanel);
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private static BufferedImage createFilterImage(int width, int height, float opacity) {
		BufferedImage filterImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = filterImage.createGraphics();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, width, height);
		g2d.dispose();
		return filterImage;
	}
}


class RoundedButton extends JButton {
	public RoundedButton(String text) {
		super(text);
		setFocusPainted(false); // Eliminar el efecto de enfoque
		setContentAreaFilled(false); // No llenar el área de contenido
		setBorderPainted(false); // Eliminar el borde pintado
		setOpaque(true);
		setForeground(Color.WHITE); // Color del texto

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(new Color(0, 102, 255)); // Color de fondo al pasar el ratón
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(new Color(0,0,128)); // Color de fondo base al salir del botón
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (getModel().isArmed()) {
			g.setColor(new Color(0, 76, 153)); // Color de fondo al presionar
		} else {
			g.setColor(getBackground());
		}

		g.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10); // Bordes redondeados
		super.paintComponent(g);
	}
}