(Sys.init) //function Sys.init 0
@4000
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 4000
@0
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
M=D //pop pointer 0
@5000
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 5000
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
@1ca506a8-782b-4d71-b097-ee573adf7226
D=A
@SP
AM=M+1
A=A-1
M=D //push returnAddress (starting call g nargs)
@LCL
D=M
@SP
AM=M+1
A=A-1
M=D //push LCL
@ARG
D=M
@SP
AM=M+1
A=A-1
M=D //push ARG
@THIS
D=M
@SP
AM=M+1
A=A-1
M=D //push THIS
@THAT
D=M
@SP
AM=M+1
A=A-1
M=D //push THAT
@SP
D=M
@5
D=D-A
@ARG
M=D //ARG = SP-nArgs-5
@SP
D=M
@LCL
M=D //LCL=SP
@Sys.main
0;JMP //goto g (ending call g nargs)
(1ca506a8-782b-4d71-b097-ee573adf7226) //call Sys.main 0
@1
D=A
@5
D=D+A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop temp 1
(LOOP) //label LOOP
@LOOP
0;JMP //goto LOOP
(Sys.main)
D=0
@SP
AM=M+1
A=A-1
M=D
D=0
@SP
AM=M+1
A=A-1
M=D
D=0
@SP
AM=M+1
A=A-1
M=D
D=0
@SP
AM=M+1
A=A-1
M=D
D=0
@SP
AM=M+1
A=A-1
M=D //function Sys.main 5
@4001
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 4001
@0
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
M=D //pop pointer 0
@5001
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 5001
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
@200
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 200
@1
D=A
@LCL
D=D+M
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop local 1
@40
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 40
@2
D=A
@LCL
D=D+M
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop local 2
@6
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 6
@3
D=A
@LCL
D=D+M
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop local 3
@123
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 123
@96c80ca6-f031-450e-b1cc-2b86bfa8770b
D=A
@SP
AM=M+1
A=A-1
M=D //push returnAddress (starting call g nargs)
@LCL
D=M
@SP
AM=M+1
A=A-1
M=D //push LCL
@ARG
D=M
@SP
AM=M+1
A=A-1
M=D //push ARG
@THIS
D=M
@SP
AM=M+1
A=A-1
M=D //push THIS
@THAT
D=M
@SP
AM=M+1
A=A-1
M=D //push THAT
@SP
D=M
@6
D=D-A
@ARG
M=D //ARG = SP-nArgs-5
@SP
D=M
@LCL
M=D //LCL=SP
@Sys.add12
0;JMP //goto g (ending call g nargs)
(96c80ca6-f031-450e-b1cc-2b86bfa8770b) //call Sys.add12 1
@0
D=A
@5
D=D+A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop temp 0
@LCL
D=M
@0
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push local 0
@LCL
D=M
@1
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push local 1
@LCL
D=M
@2
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push local 2
@LCL
D=M
@3
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push local 3
@LCL
D=M
@4
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push local 4
@SP
AM=M-1
D=M
A=A-1
M=M+D //add
@SP
AM=M-1
D=M
A=A-1
M=M+D //add
@SP
AM=M-1
D=M
A=A-1
M=M+D //add
@SP
AM=M-1
D=M
A=A-1
M=M+D //add
@LCL
D=M
@R13
M=D //frame=LCL
@5
A=D-A
D=M
@R14
M=D //retrAddr = *(frame-5)
@SP
AM=M-1
D=M
@ARG
A=M
M=D //*ARG = pop
@ARG
D=M+1
@SP
M=D //SP=ARG+1
@R13
AM=M-1
D=M
@THAT
M=D //THAT=*(frame-1)
@R13
AM=M-1
D=M
@THIS
M=D //THIS=*(frame-2)
@R13
AM=M-1
D=M
@ARG
M=D //ARG=*(frame-3)
@R13
AM=M-1
D=M
@LCL
M=D //LCL=*(frame-4)
@R14
A=M
0;JMP //goto retrAddr //return
(Sys.add12) //function Sys.add12 0
@4002
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 4002
@0
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
M=D //pop pointer 0
@5002
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 5002
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
@12
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 12
@SP
AM=M-1
D=M
A=A-1
M=M+D //add
@LCL
D=M
@R13
M=D //frame=LCL
@5
A=D-A
D=M
@R14
M=D //retrAddr = *(frame-5)
@SP
AM=M-1
D=M
@ARG
A=M
M=D //*ARG = pop
@ARG
D=M+1
@SP
M=D //SP=ARG+1
@R13
AM=M-1
D=M
@THAT
M=D //THAT=*(frame-1)
@R13
AM=M-1
D=M
@THIS
M=D //THIS=*(frame-2)
@R13
AM=M-1
D=M
@ARG
M=D //ARG=*(frame-3)
@R13
AM=M-1
D=M
@LCL
M=D //LCL=*(frame-4)
@R14
A=M
0;JMP //goto retrAddr //return