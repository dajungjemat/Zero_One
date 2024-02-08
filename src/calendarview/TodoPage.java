package calendarview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import model.ContentsDAO;
import model.ContentsDTO;
import model.MemoDAO;
import model.MemoDTO;

public class TodoPage extends JFrame{
	private JTabbedPane tabPane;
	//tap Panel
	private JPanel todoPane, memoPane, diaryPane;
	//in todoPane
	private JPanel willPane, didPane;
	//calendarPage에서 받아온 정보
	private String email = null;
	private Date date = null;
	//memoPane
	JTextArea memoBox;
	JTextArea diaryBox;
	List<ContentsDTO> willDtoList;
	List<ContentsDTO> didDtoList;
	List<ContentsDTO> checkList = new ArrayList<ContentsDTO>();
	//Style
	private TodoPage todoPage;
	private Color beigeCol = new Color(243, 232, 214);
	private Color beigeColOp = new Color(255, 249, 239);
	private Color greyCol = new Color(165, 165, 165);
	ImageIcon doneBtnImage = new ImageIcon(TodoPage.class.getResource("DONE.png"));
	ImageIcon againBtnImage = new ImageIcon(TodoPage.class.getResource("AGAIN.png"));
	ImageIcon rightBtnImage = new ImageIcon(TodoPage.class.getResource("right.png"));
	ImageIcon leftBtnImage = new ImageIcon(TodoPage.class.getResource("left.png"));
	ImageIcon createBtnImage = new ImageIcon(TodoPage.class.getResource("createBtn.png"));
	ImageIcon deleteBtnImage = new ImageIcon(TodoPage.class.getResource("deleteBtn.png"));
	ImageIcon topBackBarImage = new ImageIcon(TodoPage.class.getResource("topBackBar.png"));
	ImageIcon saveBtnImage = new ImageIcon(TodoPage.class.getResource("saveBtn.png"));
	ImageIcon topBackImage = new ImageIcon(TodoPage.class.getResource("topBack.png"));
	ImageIcon memoTopImage = new ImageIcon(TodoPage.class.getResource("memoTopImage.png"));
	ImageIcon diaryTopImage = new ImageIcon(TodoPage.class.getResource("diaryTopImage.png"));
	ImageIcon todoTopImage = new ImageIcon(TodoPage.class.getResource("todoTopImage.png"));
	
	public TodoPage(Date date,String email) {
		this.todoPage = this;
		this.date = date;
		this.email = email;
		setSize(800,1000);
		locationCenter();
		setDefaultCloseOperation(TEXT_CURSOR);

		getContentPane().add(getTabPane());	
	}
	
	public JTabbedPane getTabPane() {
		if(tabPane==null) {
			System.out.println("실행1");
			tabPane = new JTabbedPane();
			tabPane.addTab("To_Do List", getTodoPane());
			tabPane.addTab("Memo", getMemoPane());
			tabPane.addTab("Diary", getDiaryPane());
			tabPane.setBackground(beigeColOp);

		}
		return tabPane;
	}
	
	public JPanel getTodoPane() {
		if(todoPane==null) {
			todoPane = new JPanel();
			todoPane.setBackground(Color.white);
			
			JPanel timerButtonPane = new JPanel();
			timerButtonPane.setBackground(Color.white);
			timerButtonPane.setPreferredSize(new Dimension(700, 100));
			timerButtonPane.setLayout(new BorderLayout());
			
			JPanel marginPane = new JPanel(){
	            public void paintComponent(Graphics g) {
	                g.drawImage(topBackBarImage.getImage(), 0, 0, null);
	                setOpaque(false); 
	                super.paintComponent(g);
	            }
			};
			
			JPanel centerPane = new JPanel(){
	            public void paintComponent(Graphics g) {
	                g.drawImage(todoTopImage.getImage(), 0, 0, null);
	                setOpaque(false); 
	                super.paintComponent(g);
	            }
			};
			
			JButton createContentBtn = new JButton(createBtnImage);
			createContentBtn.setBackground(Color.white);
			createContentBtn.setBorder(new EmptyBorder(0,0,0,0));
			createContentBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(email);
					TodoCreatePage tcp = new TodoCreatePage(todoPage, email);
					tcp.setVisible(true);
					
				}
			});
			
			timerButtonPane.add(centerPane, BorderLayout.CENTER);
			timerButtonPane.add(marginPane, BorderLayout.NORTH);
			timerButtonPane.add(createContentBtn, BorderLayout.EAST);
			
			//버튼 업다운
			JPanel updownButtonPane = new JPanel();
			updownButtonPane.setBackground(Color.white);
			updownButtonPane.setPreferredSize(new Dimension(700, 100));
			updownButtonPane.setLayout(new FlowLayout());
			JLabel marginLabel = new JLabel();
			marginLabel.setPreferredSize(new Dimension(70,70));
			updownButtonPane.add(getDownBtn());
			updownButtonPane.add(marginLabel);
			updownButtonPane.add(getUpBtn());
			updownButtonPane.add(marginLabel);
			updownButtonPane.add(getDeleteBtn());
			
			JScrollPane willScroll = new JScrollPane(getWillPane());
			willScroll.setPreferredSize(new Dimension(700, 300));
			willScroll.setBorder(new LineBorder(beigeCol, 20, true));
			willScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			JScrollPane didScroll = new JScrollPane(getDidPane());
			didScroll.setPreferredSize(new Dimension(700, 300));
			didScroll.setBorder(new LineBorder(beigeCol, 20, true));
			didScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

			
			todoPane.add(timerButtonPane);
			todoPane.add(willScroll);
			todoPane.add(updownButtonPane);
			todoPane.add(didScroll);
		} 

		return todoPane;
	}
	//할 일 섹션
	public JPanel getWillPane() {
		if(willPane==null) {
			willPane = new JPanel();
			willPane.setBackground(Color.white);
			
			
			willPane.setLayout(new FlowLayout());
			
			//DTO List 불러오기
			System.out.println("불러오는 매소드"+date+":"+email);
			willDtoList = ContentsDAO.getInstance().getWillDTO(date, email);
			willPane.setPreferredSize(new Dimension(650, willDtoList.size()*36));
			
			System.out.println(willDtoList.size());
			for(int i=0; i<willDtoList.size();i++) {
				willPane.add(getContent(willDtoList.get(i)));
			}
			
		}
		return willPane;
	}
	
	//한 일 섹션
	public JPanel getDidPane() {
		if(didPane==null) {
			didPane = new JPanel();
			didPane.setBackground(Color.white);
			didPane.setLayout(new FlowLayout());
			
			
			didDtoList = ContentsDAO.getInstance().getDidDTO(date, email);
			didPane.setPreferredSize(new Dimension(650, didDtoList.size()*36));

			for(int i=0; i<didDtoList.size();i++) {
				didPane.add(getContent(didDtoList.get(i)));
			}
		}
		return didPane;
	}	
	
	//체크박스가 있는 패널 생성 란
	public JPanel getContent(ContentsDTO dto) {
		JPanel contentPane = new JPanel();
		contentPane.setBackground(Color.white);
		contentPane.setPreferredSize(new Dimension(600, 30));
		contentPane.setLayout(new BorderLayout());
		
		JCheckBox checkBox = new JCheckBox();
		checkBox.setBackground(Color.white);
		checkBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(checkBox.isSelected()) {
					dto.setCheck(true);
					checkList.add(dto);
				}else {
					if(checkList.size()>0) {
						for(int i=0; i<checkList.size();i++) {
							if(checkList.get(i).getContentNo()==dto.getContentNo()) {
								checkList.remove(i);
							}
						}						
					}

				}				
			}
		});
		
		contentPane.add(checkBox, BorderLayout.WEST);
		contentPane.add(new JLabel(dto.getContent()), BorderLayout.CENTER);
		if(dto.getFinishdate()==null) {
			contentPane.add(new JLabel(dto.getEndDate().toString()), BorderLayout.EAST);
		}else contentPane.add(new JLabel(dto.getFinishdate().toString()), BorderLayout.EAST);
		
	
		return contentPane;
	}
	
	public JButton getDownBtn() {
		JButton downBtn = new JButton(doneBtnImage);
		downBtn.setBackground(Color.white);
		downBtn.setIcon(doneBtnImage);
		downBtn.setBorder(new EmptyBorder(0,0,0,0));
		downBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=0; i<checkList.size();i++) {
					if(checkList.get(i).getFinishdate()==null) {
						ContentsDAO.getInstance().contentFinish(checkList.get(i),date);
					}
				}
				checkList.removeAll(checkList);
				
				refreshContentPane();      
				
			}
		});
		return downBtn;
	}
	
	public JButton getUpBtn() {
		JButton upBtn = new JButton(againBtnImage);
		upBtn.setBackground(Color.white);
		upBtn.setBorder(new EmptyBorder(0,0,0,0));

		upBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=0; i<checkList.size();i++) {
					if(checkList.get(i).getFinishdate()!=null) {
						ContentsDAO.getInstance().contentRevert(checkList.get(i));
					}
				}
				
				//이 위치에	
				checkList.removeAll(checkList);
				
				refreshContentPane();   

			}
		});
		return upBtn;
	}
	
	public JButton getDeleteBtn() {
		JButton deleteBtn = new JButton(deleteBtnImage);
		deleteBtn.setBackground(Color.white);
		deleteBtn.setBorder(new EmptyBorder(0,0,0,0));

		deleteBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				for(int i=0; i<checkList.size();i++) {
						ContentsDAO.getInstance().contentsDtoDelete(checkList.get(i));
				}
				
				checkList.removeAll(checkList);
				refreshContentPane();   

			}
		});
		return deleteBtn;
		
	}
	
	public void refreshContentPane() {
		
		TodoPage.this.tabPane.removeAll();
        TodoPage.this.remove(tabPane);
        tabPane=null; todoPane=null; memoPane=null; diaryPane=null; willPane=null; didPane=null;
        getContentPane().add(getTabPane());   
        TodoPage.this.revalidate();  	
        
	}
	//추가 버튼 눌렀을때 다이얼 로그 제작
	//-----------------------------------------------------------------------------------여기까지 todoPane----------
	

	public JPanel getMemoPane() {
		if(memoPane==null) {
			memoPane = new JPanel();
			memoPane.setSize(800,1000);
			memoPane.setBackground(Color.white);
			
			JPanel memoTopBar = new JPanel();
			memoTopBar.setPreferredSize(new Dimension(700, 100));
			memoTopBar.setBackground(Color.white);
	         memoTopBar.setLayout(new BorderLayout());
	         
	 		JPanel marginPane = new JPanel(){
	            public void paintComponent(Graphics g) {
	                g.drawImage(topBackBarImage.getImage(), 0, 0, null);
	                setOpaque(false); 
	                super.paintComponent(g);
	            }
	 		};
	 		
			JPanel centerPane = new JPanel(){
	            public void paintComponent(Graphics g) {
	                g.drawImage(memoTopImage.getImage(), 0, 0, null);
	                setOpaque(false); 
	                super.paintComponent(g);
	            }
			};
			
			
	 		memoTopBar.add(marginPane, BorderLayout.NORTH);
	 		memoTopBar.add(centerPane, BorderLayout.CENTER);
	 		
	         JLabel memodate = new JLabel(date.toString());
	         Font font = new Font("굴림 보통", Font.BOLD, 15);
	         memodate.setFont(font);
	         memodate.setAlignmentX(JLabel.CENTER);
	         memodate.setVerticalAlignment(JLabel.BOTTOM);
	         memodate.setHorizontalAlignment(JLabel.RIGHT);
	         memoTopBar.add(memodate, BorderLayout.EAST);
			
			memoBox = new JTextArea();
			MemoDTO memoDto = MemoDAO.getInstance().getMemo(date, email);
			System.out.println();
			if(memoDto!=null) {
				memoBox.setText(memoDto.getMemo());
			}
	
			JScrollPane memoScroll = new JScrollPane(memoBox);
			memoScroll.setPreferredSize(new Dimension(700, 600));
			memoScroll.setBorder(new LineBorder(beigeCol,20, true));
			
			JPanel memoUnderBar = new JPanel();
			memoUnderBar.setBackground(Color.white);
			
			JButton memoCreateBtn = new JButton(saveBtnImage);
			memoCreateBtn.setBorder(new EmptyBorder(0,0,0,0));
			memoCreateBtn.setBackground(Color.white);
			memoCreateBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					MemoDTO dto = new MemoDTO();

					dto.setEmail(email);
					dto.setMemo(memoBox.getText());
					dto.setType("memo");
					if(memoDto!=null) {
						dto.setMemoNo(memoDto.getMemoNo());
						MemoDAO.getInstance().memoDtoUpdate(dto);
					}else {
						if(memoBox.getText().toString().equals("")) {
							JOptionPane.showMessageDialog(
									TodoPage.this,
									"오늘의 메모를 입력하세요",
									"Empty memo error",
									JOptionPane.INFORMATION_MESSAGE
									);
						}else MemoDAO.getInstance().memoDtoInsert(dto, date);
					}
					
						
				}
			});
			memoUnderBar.add(memoCreateBtn);
			memoUnderBar.setPreferredSize(new Dimension(700, 100));
			
			memoPane.add(memoTopBar);
			memoPane.add(memoScroll);
			memoPane.add(memoUnderBar);
			
			
		}
		return memoPane;
	}
	
	
	//-----------------------------------------------------------------------------------여기까지 memoPane----------
	public JPanel getDiaryPane() {
		if(diaryPane==null) {
			diaryPane = new JPanel();
			diaryPane.setSize(800,1000);
			diaryPane.setBackground(Color.white);
			
			JPanel diaryTopBar = new JPanel();
			diaryTopBar.setBackground(Color.white);
	         diaryTopBar.setLayout(new BorderLayout());
	         
		 	JPanel marginPane = new JPanel(){
		           public void paintComponent(Graphics g) {
		                g.drawImage(topBackBarImage.getImage(), 0, 0, null);
		                setOpaque(false); 
		                super.paintComponent(g);
		            }
		 	};
		 	
			JPanel centerPane = new JPanel(){
	            public void paintComponent(Graphics g) {
	                g.drawImage(diaryTopImage.getImage(), 0, 0, null);
	                setOpaque(false); 
	                super.paintComponent(g);
	            }
			};
			
		 	diaryTopBar.add(marginPane, BorderLayout.NORTH);
		 	diaryTopBar.add(centerPane, BorderLayout.CENTER);
		 	
	         JLabel diarydate = new JLabel(date.toString());
	         Font font = new Font("굴림 보통", Font.BOLD, 15);
	         diarydate.setFont(font);
	         diarydate.setAlignmentX(JLabel.CENTER);
	         diarydate.setVerticalAlignment(JLabel.BOTTOM);
	         diarydate.setHorizontalAlignment(JLabel.RIGHT);
	         diaryTopBar.add(diarydate, BorderLayout.EAST);
	         diaryTopBar.setPreferredSize(new Dimension(700,100));
			
			diaryBox = new JTextArea();
			MemoDTO diaryDto = MemoDAO.getInstance().getDiary(date, email);
			if(diaryDto!=null) {
				diaryBox.setText(diaryDto.getMemo());
			}
	
			JScrollPane diaryScroll = new JScrollPane(diaryBox);
			diaryScroll.setPreferredSize(new Dimension(700, 600));	
			diaryScroll.setBorder(new LineBorder(beigeCol,20,true));
			
			JPanel diaryUnderBar = new JPanel();
			diaryUnderBar.setBackground(Color.white);
			
			JButton diaryCreateBtn = new JButton(saveBtnImage);
			diaryCreateBtn.setBorder(new EmptyBorder(0,0,0,0));
			diaryCreateBtn.setBackground(Color.white);
			
			diaryCreateBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					MemoDTO dto = new MemoDTO();

					dto.setEmail(email);
					dto.setMemo(diaryBox.getText());
					dto.setType("diary");
					if(diaryDto!=null) {
						dto.setMemoNo(diaryDto.getMemoNo());
						MemoDAO.getInstance().memoDtoUpdate(dto);
					}else { 
						if(diaryBox.getText().toString().equals("")) {
							JOptionPane.showMessageDialog(
									TodoPage.this,
									"일기 내용을 입력하세요",
									"Empty diary error",
									JOptionPane.INFORMATION_MESSAGE
									);
						}else MemoDAO.getInstance().memoDtoInsert(dto, date);
					}
				}
			});
			
			diaryUnderBar.add(diaryCreateBtn);
			diaryUnderBar.setPreferredSize(new Dimension(700, 100));
			
			diaryPane.add(diaryTopBar);
			diaryPane.add(diaryScroll);
			diaryPane.add(diaryUnderBar);
		}
		return diaryPane;
	}
	

	private void locationCenter() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int leftTopX = centerPoint.x - this.getWidth()/2;
		int leftTopY = centerPoint.y - this.getHeight()/2;
		this.setLocation(leftTopX+550, leftTopY);
	}	
}
