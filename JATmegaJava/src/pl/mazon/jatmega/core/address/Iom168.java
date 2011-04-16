package pl.mazon.jatmega.core.address;

/**
 * 
 * @author radomir.mazon
 * 
 * na podstawie pliku iom168.h i iomx8.h
 *
 */

public class Iom168 {


	protected static Address8 _SFR_IO8(int address) {
		return new Address8(address+0x20);
	}
	
	protected static Address16 _SFR_IO16(int address) {
		return new Address16(address+0x20);
	}
	
	protected static Address8 _SFR_MEM8(int address) {
		return new Address8(address);
	}
	
	protected static Address16 _SFR_MEM16(int address) {
		return new Address16(address);
	}
	
	/* I/O registers */

	/* Port B */

	public static final Address8 PINB    = _SFR_IO8 (0x03);
	/* PINB */
	public static final int  PINB7  = 7;
	public static final int  PINB6  = 6;
	public static final int  PINB5  = 5;
	public static final int  PINB4  = 4;
	public static final int  PINB3  = 3;
	public static final int  PINB2  = 2;
	public static final int  PINB1  = 1;
	public static final int  PINB0  = 0;

	public static final Address8 DDRB    = _SFR_IO8 (0x04);
	/* DDRB */
	public static final int   DDB7  =  7;
	public static final int   DDB6  =  6;
	public static final int   DDB5  =  5;
	public static final int   DDB4  =  4;
	public static final int   DDB3  =  3;
	public static final int   DDB2  =  2;
	public static final int   DDB1  =  1;
	public static final int   DDB0  =  0;

	public static final Address8 PORTB   = _SFR_IO8 (0x05);
	/* PORTB */
	public static final int   PB7   =  7;
	public static final int   PB6   =  6;
	public static final int   PB5   =  5;
	public static final int   PB4   =  4;
	public static final int   PB3   =  3;
	public static final int   PB2   =  2;
	public static final int   PB1   =  1;
	public static final int   PB0   =  0;

	/* Port C */

	public static final Address8 PINC    = _SFR_IO8 (0x06);
	/* PINC */
	public static final int   PINC6  = 6;
	public static final int   PINC5  = 5;
	public static final int   PINC4  = 4;
	public static final int   PINC3  = 3;
	public static final int   PINC2  = 2;
	public static final int   PINC1  = 1;
	public static final int   PINC0  = 0;

	public static final Address8 DDRC    = _SFR_IO8 (0x07);
	/* DDRC */
	public static final int   DDC6   = 6;
	public static final int   DDC5   = 5;
	public static final int   DDC4   = 4;
	public static final int   DDC3   = 3;
	public static final int   DDC2   = 2;
	public static final int   DDC1   = 1;
	public static final int   DDC0   = 0;

	public static final Address8 PORTC   = _SFR_IO8 (0x08);
	/* PORTC */
	public static final int   PC6    = 6;
	public static final int   PC5    = 5;
	public static final int   PC4    = 4;
	public static final int   PC3    = 3;
	public static final int   PC2    = 2;
	public static final int   PC1    = 1;
	public static final int   PC0    = 0;

	/* Port D */

	public static final Address8 PIND    = _SFR_IO8 (0x09);
	/* PIND */
	public static final int   PIND7  = 7;
	public static final int   PIND6  = 6;
	public static final int   PIND5  = 5;
	public static final int   PIND4  = 4;
	public static final int   PIND3  = 3;
	public static final int   PIND2  = 2;
	public static final int   PIND1  = 1;
	public static final int   PIND0  = 0;

	public static final Address8 DDRD    = _SFR_IO8 (0x0A);
	/* DDRD */
	public static final int   DDD7   = 7;
	public static final int   DDD6   = 6;
	public static final int   DDD5   = 5;
	public static final int   DDD4   = 4;
	public static final int   DDD3   = 3;
	public static final int   DDD2   = 2;
	public static final int   DDD1   = 1;
	public static final int   DDD0   = 0;

	public static final Address8 PORTD   = _SFR_IO8 (0x0B);
	/* PORTD */
	public static final int   PD7    = 7;
	public static final int   PD6    = 6;
	public static final int   PD5    = 5;
	public static final int   PD4    = 4;
	public static final int   PD3    = 3;
	public static final int   PD2    = 2;
	public static final int   PD1    = 1;
	public static final int   PD0    = 0;

	public static final Address8 TIFR0   = _SFR_IO8 (0x15);
	/* TIFR0 */
	public static final int   OCF0B  = 2;
	public static final int   OCF0A  = 1;
	public static final int   TOV0   = 0;

	public static final Address8 TIFR1   = _SFR_IO8 (0x16);
	/* TIFR1 */
	public static final int   ICF1   = 5;
	public static final int   OCF1B  = 2;
	public static final int   OCF1A  = 1;
	public static final int   TOV1   = 0;

	public static final Address8 TIFR2   = _SFR_IO8 (0x17);
	/* TIFR2 */
	public static final int   OCF2B  = 2;
	public static final int   OCF2A  = 1;
	public static final int   TOV2   = 0;

	public static final Address8 PCIFR   = _SFR_IO8 (0x1B);
	/* PCIFR */
	public static final int   PCIF2  = 2;
	public static final int   PCIF1  = 1;
	public static final int   PCIF0  = 0;

	public static final Address8 EIFR    = _SFR_IO8 (0x1C);
	/* EIFR */
	public static final int   INTF1  = 1;
	public static final int   INTF0  = 0;

	public static final Address8 EIMSK   = _SFR_IO8 (0x1D);
	/* EIMSK */
	public static final int   INT1   = 1;
	public static final int   INT0   = 0;

	public static final Address8 GPIOR0  = _SFR_IO8 (0x1E);

	public static final Address8 EECR    = _SFR_IO8(0x1F);
	/* EECT - EEPROM Control Register */
	public static final int   EEPM1  = 5;
	public static final int   EEPM0  = 4;
	public static final int   EERIE  = 3;
	public static final int   EEMPE  = 2;
	public static final int   EEPE   = 1;
	public static final int   EERE   = 0;

	public static final Address8 EEDR    = _SFR_IO8(0X20);

	/* Combine EEARL and EEARH */
	public static final Address16 EEAR    = _SFR_IO16(0x21);
	public static final Address8 EEARL   = _SFR_IO8(0x21);
	public static final Address8 EEARH   = _SFR_IO8(0X22);
	/* 
	Even though EEARH is not used by the mega48, the EEAR8 bit in the register
	must be written to 0, according to the datasheet, hence the EEARH register
	must be defined for the mega48.
	*/
	/* 6-char sequence denoting where to find the EEPROM registers in memory space.
	   Adresses denoted in hex syntax with uppercase letters. Used by the EEPROM
	   subroutines.
	   First two letters:  EECR address.
	   Second two letters: EEDR address.
	   Last two letters:   EEAR address.  */
	//#define __EEPROM_REG_LOCATIONS__ 1F2021


	public static final Address8 GTCCR   = _SFR_IO8 (0x23);
	/* GTCCR */
	public static final int   TSM     = 7;
	public static final int   PSRASY  = 1;
	public static final int   PSRSYNC = 0;

	public static final Address8 TCCR0A  = _SFR_IO8 (0x24);
	/* TCCR0A */
	public static final int   COM0A1 = 7;
	public static final int   COM0A0 = 6;
	public static final int   COM0B1 = 5;
	public static final int   COM0B0 = 4;
	public static final int   WGM01  = 1;
	public static final int   WGM00  = 0;

	public static final Address8 TCCR0B  = _SFR_IO8 (0x25);
	/* TCCR0A */
	public static final int   FOC0A  = 7;
	public static final int   FOC0B  = 6;
	public static final int   WGM02  = 3;
	public static final int   CS02   = 2;
	public static final int   CS01   = 1;
	public static final int   CS00   = 0;

	public static final Address8 TCNT0   = _SFR_IO8 (0x26);
	public static final Address8 OCR0A   = _SFR_IO8 (0x27);
	public static final Address8 OCR0B   = _SFR_IO8 (0x28);

	public static final Address8 GPIOR1  = _SFR_IO8 (0x2A);
	public static final Address8 GPIOR2  = _SFR_IO8 (0x2B);

	public static final Address8 SPCR    = _SFR_IO8 (0x2C);
	/* SPCR */
	public static final int SPIE  =  7;
	public static final int SPE   =  6;
	public static final int DORD  =  5;
	public static final int MSTR  =  4;
	public static final int CPOL  =  3;
	public static final int CPHA  =  2;
	public static final int SPR1  =  1;
	public static final int SPR0  =  0;

	public static final Address8 SPSR    = _SFR_IO8 (0x2D);
	/* SPSR */
	public static final int SPIF   = 7;
	public static final int WCOL   = 6;
	public static final int SPI2X  = 0;

	public static final Address8 SPDR    = _SFR_IO8 (0x2E);

	public static final Address8 ACSR    = _SFR_IO8 (0x30);
	/* ACSR */
	public static final int ACD    = 7;
	public static final int ACBG   = 6;
	public static final int ACO    = 5;
	public static final int ACI    = 4;
	public static final int ACIE   = 3;
	public static final int ACIC   = 2;
	public static final int ACIS1  = 1;
	public static final int ACIS0  = 0;

	public static final Address8 MONDR   = _SFR_IO8 (0x31);

	public static final Address8 SMCR    = _SFR_IO8 (0x33);
	/* SMCR */
	public static final int SM2    = 3;
	public static final int SM1    = 2;
	public static final int SM0    = 1;
	public static final int SE     = 0;

	public static final Address8 MCUSR   = _SFR_IO8 (0x34);
	/* MCUSR */
	public static final int WDRF   = 3;
	public static final int BORF   = 2;
	public static final int EXTRF  = 1;
	public static final int PORF   = 0;

	public static final Address8 MCUCR   = _SFR_IO8 (0x35);
	/* MCUCR */
	public static final int PUD   = 4;
	//#if defined (__AVR_ATmega88__) || defined (__AVR_ATmega168__) 
	public static final int IVSEL = 1;
	public static final int IVCE  = 0;
	//#endif

	public static final Address8 SPMCSR  = _SFR_IO8 (0x37);
	/* SPMCSR */
	public static final int SPMIE  = 7;
	//#if defined (__AVR_ATmega88__) || defined (__AVR_ATmega168__)
	public static final int RWWSB  = 6;
	public static final int RWWSRE = 4;
	//#endif
	public static final int BLBSET   = 3;
	public static final int PGWRT    = 2;
	public static final int PGERS    = 1;
	public static final int SELFPRGEN= 0;
	public static final int SPMEN    = 0;

	/* 0x3D..0x3E SP  [defined in <avr/io.h>] */
	/* 0x3F SREG      [defined in <avr/io.h>] */

	public static final Address8 WDTCSR = _SFR_MEM8 (0x60);
	/* WDTCSR */
	public static final int WDIF   = 7;
	public static final int WDIE   = 6;
	public static final int WDP3   = 5;
	public static final int WDCE   = 4;
	public static final int WDE    = 3;
	public static final int WDP2   = 2;
	public static final int WDP1   = 1;
	public static final int WDP0   = 0;

	public static final Address8 CLKPR = _SFR_MEM8 (0x61);
	/* CLKPR */
	public static final int CLKPCE = 7;
	public static final int CLKPS3 = 3;
	public static final int CLKPS2 = 2;
	public static final int CLKPS1 = 1;
	public static final int CLKPS0 = 0;

	public static final Address8 PRR  = _SFR_MEM8 (0x64);
	/* PRR */
	public static final int PRTWI   = 7;
	public static final int PRTIM2  = 6;
	public static final int PRTIM0  = 5;
	public static final int PRTIM1  = 3;
	public static final int PRSPI   = 2;
	public static final int PRUSART0= 1;
	public static final int PRADC   = 0;

	public static final Address8 OSCCAL = _SFR_MEM8 (0x66);

	public static final Address8 PCICR  = _SFR_MEM8 (0x68);
	/* PCICR */
	public static final int PCIE2  = 2;
	public static final int PCIE1  = 1;
	public static final int PCIE0  = 0;

	public static final Address8 EICRA  = _SFR_MEM8 (0x69);
	/* EICRA */
	public static final int ISC11  = 3;
	public static final int ISC10  = 2;
	public static final int ISC01  = 1;
	public static final int ISC00  = 0;

	public static final Address8 PCMSK0 = _SFR_MEM8 (0x6B);
	/* PCMSK0 */
	public static final int PCINT7   = 7;
	public static final int PCINT6   = 6;
	public static final int PCINT5   = 5;
	public static final int PCINT4   = 4;
	public static final int PCINT3   = 3;
	public static final int PCINT2   = 2;
	public static final int PCINT1   = 1;
	public static final int PCINT0   = 0;

	public static final Address8 PCMSK1 = _SFR_MEM8 (0x6C);
	/* PCMSK1 */
	public static final int PCINT14  = 6;
	public static final int PCINT13  = 5;
	public static final int PCINT12  = 4;
	public static final int PCINT11  = 3;
	public static final int PCINT10  = 2;
	public static final int PCINT9   = 1;
	public static final int PCINT8   = 0;

	public static final Address8 PCMSK2 = _SFR_MEM8 (0x6D);
	/* PCMSK2 */
	public static final int PCINT23  = 7;
	public static final int PCINT22  = 6;
	public static final int PCINT21  = 5;
	public static final int PCINT20  = 4;
	public static final int PCINT19  = 3;
	public static final int PCINT18  = 2;
	public static final int PCINT17  = 1;
	public static final int PCINT16  = 0;

	public static final Address8 TIMSK0 = _SFR_MEM8 (0x6E);
	/* TIMSK0 */
	public static final int OCIE0B = 2;
	public static final int OCIE0A = 1;
	public static final int TOIE0  = 0;

	public static final Address8 TIMSK1 = _SFR_MEM8 (0x6F);
	/* TIMSK1 */
	public static final int ICIE1  = 5;
	public static final int OCIE1B = 2;
	public static final int OCIE1A = 1;
	public static final int TOIE1  = 0;

	public static final Address8 TIMSK2 = _SFR_MEM8 (0x70);
	/* TIMSK2 */
	public static final int OCIE2B = 2;
	public static final int OCIE2A = 1;
	public static final int TOIE2  = 0;

	//#ifndef __ASSEMBLER__
	public static final Address16 ADC    = _SFR_MEM16 (0x78);
	//#endif
	public static final Address16 ADCW   = _SFR_MEM16 (0x78);
	public static final Address8 ADCL   = _SFR_MEM8 (0x78);
	public static final Address8 ADCH   = _SFR_MEM8 (0x79);

	public static final Address8 ADCSRA = _SFR_MEM8 (0x7A);
	/* ADCSRA */
	public static final int ADEN   = 7;
	public static final int ADSC   = 6;
	public static final int ADATE  = 5;
	public static final int ADIF   = 4;
	public static final int ADIE   = 3;
	public static final int ADPS2  = 2;
	public static final int ADPS1  = 1;
	public static final int ADPS0  = 0;

	public static final Address8 ADCSRB = _SFR_MEM8 (0x7B);
	/* ADCSRB */
	public static final int ACME   = 6;
	public static final int ADTS2  = 2;
	public static final int ADTS1  = 1;
	public static final int ADTS0  = 0;

	public static final Address8 ADMUX  = _SFR_MEM8 (0x7C);
	/* ADMUX */
	public static final int REFS1  = 7;
	public static final int REFS0  = 6;
	public static final int ADLAR  = 5;
	public static final int MUX3   = 3;
	public static final int MUX2   = 2;
	public static final int MUX1   = 1;
	public static final int MUX0   = 0;

	public static final Address8 DIDR0  = _SFR_MEM8 (0x7E);
	/* DIDR0 */
	public static final int ADC5D  = 5;
	public static final int ADC4D  = 4;
	public static final int ADC3D  = 3;
	public static final int ADC2D  = 2;
	public static final int ADC1D  = 1;
	public static final int ADC0D  = 0;

	public static final Address8 DIDR1  = _SFR_MEM8 (0x7F);
	/* DIDR1 */
	public static final int AIN1D  = 1;
	public static final int AIN0D  = 0;

	public static final Address8 TCCR1A = _SFR_MEM8 (0x80);
	/* TCCR1A */
	public static final int COM1A1 = 7;
	public static final int COM1A0 = 6;
	public static final int COM1B1 = 5;
	public static final int COM1B0 = 4;
	public static final int WGM11  = 1;
	public static final int WGM10  = 0;

	public static final Address8 TCCR1B = _SFR_MEM8 (0x81);
	/* TCCR1B */
	public static final int ICNC1  = 7;
	public static final int ICES1  = 6;
	public static final int WGM13  = 4;
	public static final int WGM12  = 3;
	public static final int CS12   = 2;
	public static final int CS11   = 1;
	public static final int CS10   = 0;

	public static final Address8 TCCR1C = _SFR_MEM8 (0x82);
	/* TCCR1C */
	public static final int FOC1A  = 7;
	public static final int FOC1B  = 6;

	public static final Address16 TCNT1 =  _SFR_MEM16 (0x84);
	public static final Address8 TCNT1L = _SFR_MEM8 (0x84);
	public static final Address8 TCNT1H = _SFR_MEM8 (0x85);

	public static final Address16 ICR1   = _SFR_MEM16 (0x86);
	public static final Address8 ICR1L  = _SFR_MEM8 (0x86);
	public static final Address8 ICR1H  = _SFR_MEM8 (0x87);

	public static final Address16 OCR1A  = _SFR_MEM16 (0x88);
	public static final Address8 OCR1AL = _SFR_MEM8 (0x88);
	public static final Address8 OCR1AH = _SFR_MEM8 (0x89);

	public static final Address16 OCR1B  = _SFR_MEM16 (0x8A);
	public static final Address8 OCR1BL = _SFR_MEM8 (0x8A);
	public static final Address8 OCR1BH = _SFR_MEM8 (0x8B);

	public static final Address8 TCCR2A = _SFR_MEM8 (0xB0);
	/* TCCR2A */
	public static final int COM2A1 = 7;
	public static final int COM2A0 = 6;
	public static final int COM2B1 = 5;
	public static final int COM2B0 = 4;
	public static final int WGM21  = 1;
	public static final int WGM20  = 0;

	public static final Address8 TCCR2B = _SFR_MEM8 (0xB1);
	/* TCCR2B */
	public static final int FOC2A  = 7;
	public static final int FOC2B  = 6;
	public static final int WGM22  = 3;
	public static final int CS22   = 2;
	public static final int CS21   = 1;
	public static final int CS20   = 0;

	public static final Address8 TCNT2  = _SFR_MEM8 (0xB2);
	public static final Address8 OCR2A  = _SFR_MEM8 (0xB3);
	public static final Address8 OCR2B  = _SFR_MEM8 (0xB4);

	public static final Address8 ASSR   = _SFR_MEM8 (0xB6);
	/* ASSR */
	public static final int EXCLK   = 6;
	public static final int AS2     = 5;
	public static final int TCN2UB  = 4;
	public static final int OCR2AUB = 3;
	public static final int OCR2BUB = 2;
	public static final int TCR2AUB = 1;
	public static final int TCR2BUB = 0;

	public static final Address8 TWBR   = _SFR_MEM8 (0xB8);

	public static final Address8 TWSR   = _SFR_MEM8 (0xB9);
	/* TWSR */
	public static final int TWS7   = 7;
	public static final int TWS6   = 6;
	public static final int TWS5   = 5;
	public static final int TWS4   = 4;
	public static final int TWS3   = 3;
	public static final int TWPS1  = 1;
	public static final int TWPS0  = 0;

	public static final Address8 TWAR   = _SFR_MEM8 (0xBA);
	/* TWAR */
	public static final int TWA6   = 7;
	public static final int TWA5   = 6;
	public static final int TWA4   = 5;
	public static final int TWA3   = 4;
	public static final int TWA2   = 3;
	public static final int TWA1   = 2;
	public static final int TWA0   = 1;
	public static final int TWGCE  = 0;

	public static final Address8 TWDR   = _SFR_MEM8 (0xBB);

	public static final Address8 TWCR   = _SFR_MEM8 (0xBC);
	/* TWCR */
	public static final int TWINT  = 7;
	public static final int TWEA   = 6;
	public static final int TWSTA  = 5;
	public static final int TWSTO  = 4;
	public static final int TWWC   = 3;
	public static final int TWEN   = 2;
	public static final int TWIE   = 0;

	public static final Address8 TWAMR  = _SFR_MEM8 (0xBD);
	/* TWAMR */
	public static final int TWAM6  = 7;
	public static final int TWAM5  = 6;
	public static final int TWAM4  = 5;
	public static final int TWAM3  = 4;
	public static final int TWAM2  = 3;
	public static final int TWAM1  = 2;
	public static final int TWAM0  = 1;

	public static final Address8 UCSR0A = _SFR_MEM8 (0xC0);
	/* UCSR0A */
	public static final int RXC0   = 7;
	public static final int TXC0   = 6;
	public static final int UDRE0  = 5;
	public static final int FE0    = 4;
	public static final int DOR0   = 3;
	public static final int UPE0   = 2;
	public static final int U2X0   = 1;
	public static final int MPCM0  = 0;

	public static final Address8 UCSR0B = _SFR_MEM8 (0xC1);
	/* UCSR0B */
	public static final int RXCIE0 = 7;
	public static final int TXCIE0 = 6;
	public static final int UDRIE0 = 5;
	public static final int RXEN0  = 4;
	public static final int TXEN0  = 3;
	public static final int UCSZ02 = 2;
	public static final int RXB80  = 1;
	public static final int TXB80  = 0;

	public static final Address8 UCSR0C = _SFR_MEM8 (0xC2);
	/* UCSR0C */
	public static final int UMSEL01 = 7;
	public static final int UMSEL00 = 6;
	public static final int UPM01   = 5;
	public static final int UPM00   = 4;
	public static final int USBS0   = 3;
	public static final int UCSZ01  = 2;
	public static final int UDORD0  = 2;
	public static final int UCSZ00  = 1;
	public static final int UCPHA0  = 1;
	public static final int UCPOL0  = 0;

	public static final Address16 UBRR0  = _SFR_MEM16 (0xC4);
	public static final Address8 UBRR0L = _SFR_MEM8 (0xC4);
	public static final Address8 UBRR0H = _SFR_MEM8 (0xC5);
	public static final Address8 UDR0   = _SFR_MEM8 (0xC6);

}
