package boardView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;


import model.BoardsDAO;
import model.BoardsDTO;

public class BoardApp extends JFrame {
	private BoardApp board;
	private JTable jTable;
	private JPanel pSouth;
	private RoundedButton btnInsert;
	private String email;
	private int boardNo;

	public BoardApp() {
	
		this.board = this;
		this.email = email;
		this.boardNo = boardNo;
		// this.hitcount = 0;
		this.setTitle("게시판 리스트");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(new JScrollPane(getBoardTable()), BorderLayout.CENTER);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
		this.setSize(2000, 1200);
		this.setResizable(false);
		locationCenter();
	}

	public JTable getBoardTable() {
		if (jTable == null) {
			jTable = new JTable(new NonEditableTableModel());
			
			
			DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();

			tableModel.addColumn("번호");
			tableModel.addColumn("제목");
			tableModel.addColumn("내용");
			tableModel.addColumn("날짜");
			tableModel.addColumn("글쓴이");
			tableModel.addColumn("조회수");
			refreshBoard();
			jTable = new JTable(tableModel);
			jTable.getColumn("번호").setPreferredWidth(5);
			jTable.getColumn("제목").setPreferredWidth(200);
			jTable.getColumn("내용").setPreferredWidth(300);
			jTable.getColumn("날짜").setPreferredWidth(20);
			jTable.getColumn("글쓴이").setPreferredWidth(20);
			jTable.getColumn("조회수").setPreferredWidth(5);
			jTable.setFillsViewportHeight(true);
			
			
			jTable.setRowHeight(50);
			jTable.getTableHeader().setReorderingAllowed(false);
			jTable.setDefaultRenderer(Object.class, new EvenOddRenderer());
			jTable.setDragEnabled(false);
			
			
			JTableHeader header = jTable.getTableHeader();

			header.setBackground(new Color(243, 232, 214));
			header.setForeground(new Color(165, 165, 165));
			header.setPreferredSize(new Dimension(header.getPreferredSize().width, 50));

			TableColumnModel columnModel = jTable.getColumnModel();
			for (int i = 0; i < columnModel.getColumnCount(); i++) {
				TableColumn column = columnModel.getColumn(i);
				column.setResizable(false);
			}

			jTable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {

					int rowIndex = jTable.getSelectedRow();
					if (rowIndex != -1) {
						int boardNo = (int) jTable.getValueAt(rowIndex, 0);
						String email = (String) jTable.getValueAt(rowIndex, 4);
						CommentDialog commentDialog = new CommentDialog(board, boardNo, email);
						commentDialog.setVisible(true);
						refreshBoard();
					}
				}

			});
		}
		return jTable;
	} //게시판 첫 화면 테이블

	public void refreshBoard() {
		DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
		tableModel.setNumRows(0);
		for (BoardsDTO dto : BoardsDAO.getInstance().getBoards()) {
			Object[] rowData = { dto.getBoardNo(), dto.getTitle(), dto.getBoardContent(), dto.getBoardDate(),
					dto.getEmail(), dto.getHitcount() };
			tableModel.addRow(rowData);
		}
	}//새로고침

	public JPanel getPSouth() {
		if (pSouth == null) {
			/* 코드 추가 */
			pSouth = new JPanel();
			pSouth.add(getBtnInsert());
			
			pSouth.setBackground(Color.WHITE);
		}
		return pSouth;
	}//게시글 작성하는 추가버튼있는 패널

	public RoundedButton getBtnInsert() {
		if (btnInsert == null) {
			/* 코드 추가 */
			btnInsert = new RoundedButton();
			btnInsert.setText("추가");
			
			
			btnInsert.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					new PostDialog(board, email, boardNo).setVisible(true);

				}
			});

		}
		return btnInsert;
	}//postDialog를 모달창으로 띄우는 버튼 

	private void locationCenter() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int leftTopX = centerPoint.x - this.getWidth() / 2;
		int leftTopY = centerPoint.y - this.getHeight() / 2;
		this.setLocation(leftTopX, leftTopY);
	}

	public class EvenOddRenderer extends DefaultTableCellRenderer {
		private static final Color EVEN_COLOR = new Color(255, 249, 239);
		private static final Color ODD_COLOR = Color.WHITE;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component component = super.getTableCellRendererComponent(jTable, value, isSelected, hasFocus, row, column);
			if (!isSelected) {
				Color bgColor = (row % 2 == 0) ? EVEN_COLOR : ODD_COLOR;
				component.setBackground(bgColor);
				setHorizontalAlignment(SwingConstants.CENTER);
			}
			return component;
		}//테이블 열마다 색상변경, 글자 가운데 배치 
	}
	

	public class NonEditableTableModel extends DefaultTableModel {
		@Override
		public boolean isCellEditable(int row, int column) {

			return false;
		}//테이블 수정불가능
	}

	class RoundedButton extends JButton {
		public RoundedButton() {
			super();
			decorate();
		}

		public RoundedButton(String text) {
			super(text);
			decorate();
		}

		public RoundedButton(Action action) {
			super(action);
			decorate();
		}

		public RoundedButton(Icon icon) {
			super(icon);
			decorate();
		}

		public RoundedButton(String text, Icon icon) {
			super(text, icon);
			decorate();
		}

		protected void decorate() {
			setBorderPainted(false);
			setOpaque(false);
		}

		@Override
		protected void paintComponent(Graphics g) {
			Color c = new Color(243, 232, 214); // 배경색 결정
			Color o = new Color(165, 165, 165); // 글자색 결정
			int width = getWidth();
			int height = getHeight();
			Graphics2D graphics = (Graphics2D) g;
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			if (getModel().isArmed()) {
				graphics.setColor(c.darker());
			} else if (getModel().isRollover()) {
				graphics.setColor(c.brighter());
			} else {
				graphics.setColor(c);
			}
			graphics.fillRoundRect(0, 0, width, height, 10, 10);
			FontMetrics fontMetrics = graphics.getFontMetrics();
			Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds();
			int textX = (width - stringBounds.width) / 2;
			int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent();
			graphics.setColor(o);
			graphics.setFont(getFont());
			graphics.drawString(getText(), textX, textY);
			graphics.dispose();
			super.paintComponent(g);
		}//버튼 양식
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			BoardApp board = new BoardApp();
			board.setVisible(true);
		});
	}

}
