@ARG
D=M
@1
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push argument 1
@1
D=A
@3
D=D+A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop pointer 1
@0
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 0
@0
D=A
@THAT
D=D+M
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop that 0
@1
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 1
@1
D=A
@THAT
D=D+M
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop that 1
@ARG
D=M
@0
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push argument 0
@2
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 2
@SP
AM=M-1
D=M
A=A-1
M=M-D //sub
@0
D=A
@ARG
D=D+M
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop argument 0
(LOOP) //label LOOP
@ARG
D=M
@0
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push argument 0
@SP
AM=M-1
D=M
@COMPUTE_ELEMENT
D;JNE //if-goto COMPUTE_ELEMENT
@END
0;JMP //goto END
(COMPUTE_ELEMENT) //label COMPUTE_ELEMENT
@THAT
D=M
@0
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push that 0
@THAT
D=M
@1
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push that 1
@SP
AM=M-1
D=M
A=A-1
M=M+D //add
@2
D=A
@THAT
D=D+M
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop that 2
@3
D=A
@1
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push pointer 1
@1
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 1
@SP
AM=M-1
D=M
A=A-1
M=M+D //add
@1
D=A
@3
D=D+A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop pointer 1
@ARG
D=M
@0
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push argument 0
@1
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 1
@SP
AM=M-1
D=M
A=A-1
M=M-D //sub
@0
D=A
@ARG
D=D+M
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop argument 0
@LOOP
0;JMP //goto LOOP
(END) //label END