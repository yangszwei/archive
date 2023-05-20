#!/usr/bin/env python3

from enum import Enum
from typing import Final


class PrettyPrint:
    """Prints messages with colors."""
    @staticmethod
    def info(msg: str, *args, **kwargs) -> None:
        print('\033[36m' + msg + '\033[0m', *args, **kwargs)

    @staticmethod
    def success(msg: str, *args, **kwargs) -> None:
        print('\033[32m' + msg + '\033[0m', *args, **kwargs)

    @staticmethod
    def error(msg: str, *args, **kwargs) -> None:
        print('\033[31m' + msg + '\033[0m', *args, **kwargs)

    @staticmethod
    def divider(*args, **kwargs) -> None:
        print('-' * 35, *args, **kwargs)


class Signal(Enum):
    """Used to control the flow of the main loop."""
    EXIT: Final = 0
    CONTINUE: Final = 1
    RESET: Final = 2


class TransResult(Enum):
    """Transaction result (deposit or withdraw)."""
    OK: Final = None
    BAD_AMOUNT: Final = '輸入錯誤，金額需為大於 0 的整數！'
    INSUFFICIENT_BALANCE: Final = '帳戶餘額不足！'


class User:
    __balance: int = 100
    __password: str = 'ILOVEYOU'

    @property
    def balance(self) -> int:
        return self.__balance

    def authenticate(self, password: str) -> bool:
        return password == self.__password

    def change_password(self, new_password: str, new_password_again: str) -> bool:
        if new_password == new_password_again:
            self.__password = new_password
            return True
        return False

    def deposit(self, amount: int) -> TransResult:
        if amount <= 0:
            return TransResult.BAD_AMOUNT
        self.__balance += amount
        return TransResult.OK

    def withdraw(self, amount: int) -> TransResult:
        if amount <= 0:
            return TransResult.BAD_AMOUNT
        if self.__balance < amount:
            return TransResult.INSUFFICIENT_BALANCE
        self.__balance -= amount
        return TransResult.OK


class ATM:
    _user: User

    def __init__(self, user: User):
        self._user = user

    @staticmethod
    def _welcome() -> None:
        """Welcome screen"""
        PrettyPrint.info('\n歡迎使用 NTUNHS_DuhWell 銀行 ATM')

    @staticmethod
    def _sign_in(user: User) -> Signal:
        """Sign in screen
        :param user: User object.
        :return: `CONTINUE` signal if signed in successfully, `EXIT` if not.
        """
        try:
            max_try: Final = 3
            for i in range(max_try):
                password = input('請輸入密碼：')
                if user.authenticate(password):
                    PrettyPrint.success('登入成功！')
                    return Signal.CONTINUE
                if i < 2:
                    PrettyPrint.error(f'密碼錯誤！您還有 {max_try - i - 1} 次機會。')
            PrettyPrint.error('登入失敗！')
            return Signal.EXIT  # To restart the main loop, set this to `Signal.RESET`.
        except KeyboardInterrupt:
            PrettyPrint.info('\n已取消登入。')  # Break line after ^C
            return Signal.EXIT

    @staticmethod
    def _main_menu(self: 'ATM') -> Signal:
        """Main menu
        :param self: ATM object.
        :return: Signal returned by the selected function.
        """
        try:
            choice = int(input('請選擇功能（1. 存款 2. 提款 3. 密碼更改 4. 離開）：'))
            if not 1 <= choice <= 4:
                raise ValueError
            return [self._deposit, self._withdraw, self._change_password, lambda: Signal.EXIT][choice - 1]()
        except ValueError:
            PrettyPrint.error('輸入錯誤，請重新輸入！')
            return Signal.CONTINUE
        except KeyboardInterrupt:
            print()  # Break line after ^C
            return Signal.EXIT

    @staticmethod
    def _continue_menu() -> Signal:
        """Continue menu, defaults to continue (Y)."""
        choice = input('是否要繼續使用 ATM（Y/n）：').lower()
        return Signal.EXIT if choice == 'n' else Signal.CONTINUE

    @staticmethod
    def _exit() -> Signal:
        """Exit screen"""
        PrettyPrint.info('Bye!')
        return Signal.EXIT

    def _main(self) -> Signal:
        """Main loop"""
        self._welcome()

        PrettyPrint.divider()
        result = self._sign_in(self._user)
        if result != Signal.CONTINUE:
            return result

        PrettyPrint.divider()
        while True:
            result = self._main_menu(self)
            if result == Signal.EXIT:
                return self._exit()
            elif result == Signal.CONTINUE:
                PrettyPrint.divider()
            elif result == Signal.RESET:
                return Signal.RESET

    def _deposit(self) -> Signal:
        """Deposit UI"""
        try:
            amount = input('請輸入存款金額（以 \033[36mq\033[0m 取消）：')
            if amount == 'q':
                PrettyPrint.info('已取消存款。')
                return Signal.CONTINUE
            result = self._user.deposit(int(amount))
            if result != TransResult.OK:
                PrettyPrint.error(result.value)
                return self._deposit()  # Retry
            PrettyPrint.success(f'存款成功！帳戶餘額：{self._user.balance} 元')
            return self._continue_menu()
        except ValueError:
            PrettyPrint.error(TransResult.BAD_AMOUNT.value)
            return self._deposit()  # Retry
        except KeyboardInterrupt:
            PrettyPrint.info('\n已取消存款。')  # Break line after ^C
            return Signal.CONTINUE

    def _withdraw(self) -> Signal:
        """Withdraw UI"""
        try:
            amount = input('請輸入提款金額（以 \033[36mq\033[0m 取消）：')
            if amount == 'q':
                PrettyPrint.info('已取消提款。')
                return Signal.CONTINUE
            result = self._user.withdraw(int(amount))
            if result != TransResult.OK:
                PrettyPrint.error(result.value)
                return self._withdraw()  # Retry
            PrettyPrint.success(f'提款成功！帳戶餘額：{self._user.balance} 元')
            return self._continue_menu()
        except ValueError:
            PrettyPrint.error(TransResult.BAD_AMOUNT.value)
            return self._withdraw()  # Retry
        except KeyboardInterrupt:
            PrettyPrint.info('\n已取消提款。')  # Break line after ^C
            return Signal.CONTINUE

    def _change_password(self) -> Signal:
        """Change password UI."""
        try:
            new_password = input('請輸入新密碼（以 \033[36m[Ctrl] + [C]\033[0m 取消）：')
            new_password_again = input('請再次輸入新密碼：')
            if self._user.change_password(new_password, new_password_again):
                PrettyPrint.success('密碼更改成功，請重新登入！')
                return Signal.RESET
            PrettyPrint.error('兩次輸入的密碼不相同！')
            return self._change_password()
        except KeyboardInterrupt:
            PrettyPrint.info('\n已取消密碼更改。')  # Break line after ^C
            return Signal.CONTINUE

    def start(self) -> None:
        """entry point
        (restarts the main loop on `RESET` signal.)
        """
        while self._main() == Signal.RESET:
            pass


if __name__ == '__main__':
    ATM(User()).start()
