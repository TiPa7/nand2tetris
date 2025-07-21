(LOOP)
    @0
    D=M
    @INCRAM2
    D;JGT
    @END
    0;JMP

(INCRAM2)
    @1
    D=M
    @2
    D=D+M
    M=D
    @DECRAM0
    0;JMP

(DECRAM0)
    @0
    M=M-1
    @LOOP
    0;JMP

(END)
    @END
    0;JMP
