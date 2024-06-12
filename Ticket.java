package railway_ticket_booking;

public class Ticket 
{
	private int ticketNo;
	private String status;
	private int seatNo;

	public int getTicketNo()
	{
		return this.ticketNo;
	}
	
	public int getSeatNo()
	{
		return this.seatNo;
	}
	public void setStatus(String status)
	{
		this.status=status;
	}
	
	public String getStatus()
	{
		return this.status;
	}
	
	public void setSeatNo(int seatNo)
	{
		this.seatNo=seatNo;
	}
	
	Ticket(int ticketNo,String status,int seatNo)
	{
		this.ticketNo=ticketNo;
		this.status=status;
		this.seatNo=seatNo;
	}
}
