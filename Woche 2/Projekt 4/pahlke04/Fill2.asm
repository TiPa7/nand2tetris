// Init
    @SCREEN
    D=A
    @addr
    M=D         // addr = SCREEN (16384)
    @WHITE
    M=-1        // WHITE = 0xFFFF
    @BLACK
    M=0         // BLACK = 0x0000

// Check Keyboard
    @KBD
    D=M
    @FILLWHITE
    D;JEQ       // if KBD == 0 → Weiß
    @FILLBLACK
    0;JMP       // sonst Schwarz

// Fill white
(FILLWHITE)
    @addr
    D=M
    @KBD
    D=D-A
    @END
    D;JGE
    @WHITE
    D=M
    @addr
    A=M
    M=D
    @addr
    M=M+1
    @FILLWHITE
    0;JMP

// Fill black
(FILLBLACK)
    @addr
    D=M
    @KBD
    D=D-A
    @END
    D;JGE
    @BLACK
    D=M
    @addr
    A=M
    M=D
    @addr
    M=M+1
    @FILLBLACK
    0;JMP

// Stop program
(END)
    @END
    0;JMP
