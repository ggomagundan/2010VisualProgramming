package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import menus.GEMenuBar;
import menus.GEMenuFile;

import constants.GEConstants;

public class GECal extends JPanel {
	
	private int yy;
	private int mm, dd;
	private JButton labs[][];
	private int leadGap = 0;
	Calendar calendar = new GregorianCalendar();
	private JButton b0;
	private JComboBox monthChoice;
	private JComboBox yearChoice;
	String[] months = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
			"11", "12" };
	private final static int dom[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31,
			30, 31 };
	private String dateText = null;
	public ArrayList shapelist;
	private GEDrawingPanel drawingPanel; // //
	private GEMenuBar menuBar;
	private GEMenuFile fileMenu;

	public GECal() {
		super();
		shapelist = new ArrayList();
		for (int i = 0; i < 1300; i++) {
			shapelist.add(null);
		}
		setYYMMDD(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		BuildGUI();
		recompute();
	}

	public void init(GEDrawingPanel dp) {
		// TODO Auto-generated method stub
		this.drawingPanel = dp;
		StringBuffer buffer = new StringBuffer();
		String temp = "" + (mm + 1);
		if (temp.length() <= 1) {
			buffer.append("0").append(mm + 1);
		} else {
			buffer.append(mm + 1);
		}
		temp = "" + dd;
		if (temp.length() <= 1) {
			buffer.append("0").append(dd);
		} else {
			buffer.append(dd);
		}
		dateText = buffer.toString().trim();
	}

	public void init(GEMenuBar menuBar) {
		this.menuBar = menuBar;
	}

	public void init(GEMenuFile fileMenu) {
		this.fileMenu = fileMenu;
	}

	public GECal(int year, int month, int today) {
		super();
		setYYMMDD(year, month, today);
		BuildGUI();
		recompute();
	}

	private void setYYMMDD(int year, int month, int today) {
		yy = year;
		mm = month;
		dd = today;

	}

	public void BuildGUI() {
		setBorder(BorderFactory.createEtchedBorder());
		setLayout(new BorderLayout());
		JPanel tp = new JPanel();
		monthChoice = new JComboBox();

		for (int i = 0; i < months.length; i++) {
			monthChoice.addItem(months[i]);
		}

		monthChoice.setSelectedItem(months[mm]);

		monthChoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = monthChoice.getSelectedIndex();
				if (i >= 0) {
					mm = i;
					recompute();
				}
			}
		});

		monthChoice.getAccessibleContext().setAccessibleName("Months");
		monthChoice.getAccessibleContext().setAccessibleDescription(
				"Choose a month of the year");
		tp.add(yearChoice = new JComboBox());
		yearChoice.setEditable(true);

		for (int i = yy - 5; i < yy + 5; i++) {
			yearChoice.addItem(Integer.toString(i));
		}

		yearChoice.setSelectedItem(Integer.toString(yy));
		yearChoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = yearChoice.getSelectedIndex();
				if (i >= 0) {
					yy = Integer.parseInt(yearChoice.getSelectedItem()
							.toString());
					recompute();

					JFrame f = new JFrame();
					int chk = JOptionPane.showConfirmDialog(f,
							"다른 년도 선택시 저장하시고, 불러오셔야 합니다.", "년도이동", 2);
					if (chk == 0) {
						JOptionPane.showMessageDialog(f,
								"저장창이 열리고 열기창이 열립니다. 취소하고 싶으시면 취소를 눌러주세요.");
						fileMenu.save();
						shapelist = new ArrayList();
						for (int j = 0; i < 1300; i++) {
							shapelist.add(null);
						}
						fileMenu.open();
					}
				}
			}
		});

		tp.add(monthChoice);
		add(BorderLayout.CENTER, tp);
		JPanel bp = new JPanel();
		bp.setLayout(new GridLayout(7, 7));
		labs = new JButton[6][7]; // first row is days

		bp.add(b0 = new JButton("일"));
		bp.add(new JButton("월"));
		bp.add(new JButton("화"));
		bp.add(new JButton("수"));
		bp.add(new JButton("목"));
		bp.add(new JButton("금"));
		bp.add(new JButton("토"));

		ActionListener dateSetter = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String num = e.getActionCommand();
				drawingPanel.getpanel(getDateText());
				if (!num.equals("")) {
					setDayActive(Integer.parseInt(num));
					StringBuffer buffer = new StringBuffer();
					String temp = "" + (mm + 1);
					if (temp.length() <= 1) {
						buffer.append("0").append(mm + 1);
					} else {
						buffer.append(mm + 1);
					}
					temp = "" + dd;
					if (temp.length() <= 1) {
						buffer.append("0").append(dd);
					} else {
						buffer.append(dd);
					}
					dateText = buffer.toString().trim();
				}
			}
		};

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				bp.add(labs[i][j] = new JButton(""));
				labs[i][j].addActionListener(dateSetter);
			}
			add(BorderLayout.SOUTH, bp);
		}
	}

	protected void recompute() {
		if (mm < 0 || mm > 11) {
			throw new IllegalArgumentException("Month " + mm
					+ " bad, must be 0-11");
		}
		
		clearDayActive();
		calendar = new GregorianCalendar(yy, mm, dd);
		leadGap = new GregorianCalendar(yy, mm, 1).get(Calendar.DAY_OF_WEEK) - 1;
		int daysInMonth = dom[mm];

		if (isLeap(calendar.get(Calendar.YEAR)) && mm == 1) {
			++daysInMonth;
		}

		for (int k = 0; k < labs.length; k++) {
			for (int r = 0; r < labs[k].length; r++) {
				labs[k][r].setText("");
			}
		}

		for (int i = 0; i < leadGap; i++) {
			labs[0][i].setText("");
		}

		for (int i = 1; i <= daysInMonth; i++) {
			JButton b = labs[(leadGap + i - 1) / 7][(leadGap + i - 1) % 7];
			b.setText(Integer.toString(i));
		}

		for (int i = leadGap + 1 + daysInMonth; i < 6 * 7; i++) {
			labs[(i) / 7][(i) % 7].setText("");
		}

		if (GEConstants.ThisYear == yy && mm == GEConstants.ThisMonth)
			setDayActive(dd);
		repaint();
	}

	public boolean isLeap(int year) {
		if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
			return true;
		}
		return false;
	}

	public void setDate(int yy, int mm, int dd) {
		this.yy = yy;
		this.mm = mm;
		this.dd = dd;
		recompute();
	}

	private void clearDayActive() {
		JButton b;
		if (activeDay > 0) {
			b = labs[(leadGap + activeDay - 1) / 7][(leadGap + activeDay - 1) % 7];
			b.setBackground(b0.getBackground());
			b.repaint();
			activeDay = -1;
		}
	}

	private int activeDay = -1;

	public void setDayActive(int newDay) {
		clearDayActive();
		if (newDay <= 0) {
			dd = new GregorianCalendar().get(Calendar.DAY_OF_MONTH);
		} else {
			dd = newDay;
		}
		Component square = labs[(leadGap + newDay - 1) / 7][(leadGap + newDay - 1) % 7];
		square.setBackground(Color.PINK);
		square.repaint();
		activeDay = newDay;
	}

	public int getDateText() {
		int dates = Integer.parseInt(dateText);
		return dates;
	}
}