import React, { useState } from 'react';
import styled from 'styled-components';
import { useHistory } from 'react-router-dom';
// import fetchLogin from './service';

function Login() {
  const history = useHistory();
  const moveHome = () => {
    // 로그인한 사람의 정보를 세션에 올려놓음
    sessionStorage.setItem('homePageHost', '5_waterglass');
    history.push({
      pathname: '/home',
    });
  };

  const [account, setAccount] = useState({
    id: '',
    password: '',
  });

  const onChangeAccount = e => {
    setAccount({ ...account, [e.target.name]: e.target.value });
  };

  const onSubmitAccount = async () => {
    try {
      // const JWT = await fetchLogin(account);

      // sessionStorage.setItem('jwt', JWT);
      history.replace('/home');
    } catch (error) {
      // window.alert(error);
    }
  };

  return (
    <Container>
      <Container2>
        <DivStyle>
          <div
            style={{
              display: 'flex',
              flexDirection: 'column',
              justifyContent: 'flex-start',
              padding: '20px',
              width: '400px',
            }}
          >
            <Input
              id="id"
              name="id"
              placeholder="아이디"
              onChange={onChangeAccount}
            />
            <Input
              id="password"
              name="password"
              placeholder="비밀번호"
              onChange={onChangeAccount}
            />
          </div>
          <div
            style={{
              display: 'flex',
              flexDirection: 'column',
              justifyContent: 'center',
              alignItems: 'center',
            }}
          >
            <LoginButton onClick={onSubmitAccount}>로그인</LoginButton>
            <ForgotPwd>비밀번호를 잊으셨나요?</ForgotPwd>
          </div>
        </DivStyle>
        <DivStyle2>
          <hr style={{ width: '300px' }} />
          <OrDiv>또는</OrDiv>
          <hr width="300px" />
        </DivStyle2>
        <GoogleLoginButton>구글 로그인</GoogleLoginButton>
        <JoinText>
          아직 계정이 없으신가요? <Join>가입하기</Join>
        </JoinText>
        <button type="button" onClick={() => moveHome()}>
          click
        </button>
      </Container2>
    </Container>
  );
}

export default Login;

/* CSS */
const Container = styled.div`
  width: 100%;
  height: 100%;

  display: flex;
  justify-content: center;
  align-items: center;
`;

const Container2 = styled.div`
  width: 60%;
  max-width: 1200px;
  min-width: 1000px;
  height: 60%;
  max-height: 700px;
  min-height: 600px;
  background-color: #ffffff;
  border: solid 1px #dadada;
  border-radius: 10px;

  display: flex;
  flex-direction: column;
  justify-content: center;
`;

const Input = styled.input`
  width: 100%;
  height: 45px;
  padding: 20px;
  margin-bottom: 20px;

  border: solid 1px #dadada;
  background: #fff;
  box-sizing: border-box;
  border-radius: 10px;
  &:focus {
    border: none;
    outline: 1px solid rgba(75, 182, 209, 0.87);
  }
`;
const DivStyle = styled.div`
  display: flex;
  justify-content: space-around;
  align-items: center;
  padding: 0 180px;
`;

const DivStyle2 = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  margin-top: 30px;
`;

const LoginButton = styled.button`
  font-size: 21px;
  font-weight: 600;
  margin-bottom: 10px;

  width: 155px;
  height: 140px;

  cursor: pointer;
  text-align: center;
  color: #fff;
  border: none;
  background-color: rgba(75, 182, 209, 0.87);
  border-radius: 10px;
`;
const GoogleLoginButton = styled.button`
  display: block;
  font-size: 21px;
  font-weight: 600;
  margin: 36px auto;
  width: 394px;
  height: 73px;

  cursor: pointer;
  color: #fff;
  border: none;
  background-color: rgba(75, 182, 209, 0.87);
  border-radius: 10px;
`;

const ForgotPwd = styled.div`
  margin: 24px 0px 24px 0px;
  font-size: 16px;
  text-align: right;
  cursor: pointer;
`;

const OrDiv = styled.div`
  text-align: center;
  margin: 0 20px;
`;

const JoinText = styled.div`
  text-align: center;
`;

const Join = styled.a`
  color: rgba(75, 182, 209, 0.87);
  text-decoration: none;
  cursor: pointer;
`;
