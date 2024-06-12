package railway_ticket_booking;
import java.util.ArrayList;

public class Pnr {
    private int pnrId;
	private char from;
	private char to;
	private int totalTickets;
	ArrayList<Ticket> tickets=new ArrayList<>();
	
	public void setPnrId(int pnrId)
	{
		this.pnrId=pnrId;
	}
	public void setFrom(char from)
	{
		this.from=from;
	}
	public void setTo(char to)
	{
		this.to=to;
	}
	public void setTotalTickets(int totalTickets)
	{
		this.totalTickets=totalTickets;
	}
	public void setTickets(ArrayList tickets)
	{
		this.tickets=tickets;
	}
	public int getPnrId()
	{
		return this.pnrId;
	}
	public char getFrom()
	{
		return this.from;
	}
	public char getTo()
	{
		return this.to;
	}
	public int getTotalTickets()
	{
		return this.totalTickets;
	}
	public ArrayList getTickets()
	{
		return tickets;
	}
}
