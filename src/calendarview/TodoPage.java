package calendarview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

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
	private TodoPage todoPage;
	
	public TodoPage(Date date,String email) {
		this.todoPage = this;
		this.date = date;
		this.email = email;
		setSize(800,1000);
		locationCenter();
		getContentPane().add(getTabPane());	
	}
	
	public JTabbedPane getTabPane() {
		if(tabPane==null) {
			System.out.println("실행1");
			tabPane = new JTabbedPane();
			tabPane.addTab("To_Do List", getTodoPane());
			tabPane.addTab("Memo", getMemoPane());
			tabPane.addTab("Diary", getDiaryPane());

		}
		return tabPane;
	}
	
	public JPanel getTodoPane() {
		if(todoPane==null) {
			todoPane = new JPanel();
			JPanel timerButtonPane = new JPanel();
			timerButtonPane.setPreferredSize(new Dimension(700, 100));
			timerButtonPane.setLayout(new FlowLayout(FlowLayout.LEFT));
			JButton createContentBtn = new JButton("생성");
//			createContentBtn.setVerticalAlignment(JButton.BOTTOM);
//			createContentBtn.setAlignmentX(JButton.RIGHT);
			createContentBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					TodoCreatePage tcp = new TodoCreatePage(todoPage, email);
					tcp.setVisible(true);
					
				}
			});
			timerButtonPane.add(createContentBtn);
			
			JPanel updownButtonPane = new JPanel();
			updownButtonPane.setPreferredSize(new Dimension(700, 100));
			updownButtonPane.add(getDownBtn());
			updownButtonPane.add(getUpBtn());
			
			JScrollPane willScroll = new JScrollPane(getWillPane());
			willScroll.setPreferredSize(new Dimension(700, 300));
			willScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			JScrollPane didScroll = new JScrollPane(getDidPane());
			didScroll.setPreferredSize(new Dimension(700, 300));
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
			willPane.setBackground(Color.red);
			willPane.setLayout(new FlowLayout());
			
			//DTO List 불러오기
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
			didPane.setBackground(Color.blue);
			didPane.setLayout(new FlowLayout());
			
			
			didDtoList = ContentsDAO.getInstance().getDidDTO(date, email);
			didPane.setPreferredSize(new Dimension(650, didDtoList.size()*36));

			System.out.println(didDtoList.size()+" "+date+" "+email);
			for(int i=0; i<didDtoList.size();i++) {
				didPane.add(getContent(didDtoList.get(i)));
			}
		}
		return didPane;
	}	
	
	//체크박스가 있는 패널 생성 란
	public JPanel getContent(ContentsDTO dto) {
		JPanel contentPane = new JPanel();
		contentPane.setPreferredSize(new Dimension(600, 30));
		contentPane.setLayout(new BorderLayout());
		JCheckBox checkBox = new JCheckBox();
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
		contentPane.add(new JLabel(dto.getTimer()), BorderLayout.EAST);
	
		return contentPane;
	}
	
	public JButton getDownBtn() {
		JButton downBtn = new JButton("내리기");
		downBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=0; i<checkList.size();i++) {
					if(checkList.get(i).getFinishdate()==null) {
						ContentsDAO.getInstance().contentFinish(checkList.get(i));
					}
				}
				checkList.removeAll(checkList);
				
				refreshContentPane();      
				
			}
		});
		return downBtn;
	}
	
	public JButton getUpBtn() {
		JButton upBtn = new JButton("올리기");
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
			
			JPanel memoTopBar = new JPanel();
	         memoTopBar.setLayout(new BorderLayout());
	         JLabel memodate = new JLabel(date.toString());
	         Font font = new Font("굴림 보통", Font.BOLD, 15);
	         memodate.setFont(font);
	         memodate.setAlignmentX(JLabel.CENTER);
	         memodate.setVerticalAlignment(JLabel.BOTTOM);
	         memodate.setHorizontalAlignment(JLabel.RIGHT);
	         memoTopBar.add(memodate);
	         memoTopBar.setPreferredSize(new Dimension(600,100));
			
			memoBox = new JTextArea();
			MemoDTO memoDto = MemoDAO.getInstance().getMemo(date, email);
			System.out.println();
			if(memoDto!=null) {
				memoBox.setText(memoDto.getMemo());
			}
	
			JScrollPane memoScroll = new JScrollPane(memoBox);
			memoScroll.setPreferredSize(new Dimension(600, 400));	
			
			JPanel memoUnderBar = new JPanel();
			JButton memoCreateBtn = new JButton("저장");
			memoCreateBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					MemoDTO dto = new MemoDTO();

					dto.setEmail(email);
					dto.setMemo(memoBox.getText());
					dto.setType("memo");
					if(memoDto!=null) dto.setMemoNo(memoDto.getMemoNo());
					System.out.println(dto.getMemoNo());
					System.out.println(dto.getEmail());
					System.out.println(dto.getMemo());
					System.out.println(dto.getType());
					System.out.println(memoDto);
					if(memoDto==null) 
						MemoDAO.getInstance().memoDtoInsert(dto);
					else 
						MemoDAO.getInstance().memoDtoUpdate(dto);
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
			
			JPanel diaryTopBar = new JPanel();
	         diaryTopBar.setLayout(new BorderLayout());
	         JLabel diarydate = new JLabel(date.toString());
	         Font font = new Font("굴림 보통", Font.BOLD, 15);
	         diarydate.setFont(font);
	         diarydate.setAlignmentX(JLabel.CENTER);
	         diarydate.setVerticalAlignment(JLabel.BOTTOM);
	         diarydate.setHorizontalAlignment(JLabel.RIGHT);
	         diaryTopBar.add(diarydate);
	         diaryTopBar.setPreferredSize(new Dimension(600,100));
			
			diaryBox = new JTextArea();
			MemoDTO diaryDto = MemoDAO.getInstance().getDiary(date, email);
			if(diaryDto!=null) {
				diaryBox.setText(diaryDto.getMemo());
			}
	
			JScrollPane diaryScroll = new JScrollPane(diaryBox);
			diaryScroll.setPreferredSize(new Dimension(600, 400));	
			
			JPanel diaryUnderBar = new JPanel();
			JButton diaryCreateBtn = new JButton("저장");
			diaryCreateBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					MemoDTO dto = new MemoDTO();

					dto.setEmail(email);
					dto.setMemo(diaryBox.getText());
					dto.setType("diary");
					if(diaryDto!=null) dto.setMemoNo(diaryDto.getMemoNo());
					System.out.println(dto.getMemoNo());
					System.out.println(dto.getEmail());
					System.out.println(dto.getMemo());
					System.out.println(dto.getType());
					System.out.println(diaryDto);
					if(diaryDto==null) 
						MemoDAO.getInstance().memoDtoInsert(dto);
					else 
						MemoDAO.getInstance().memoDtoUpdate(dto);
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
