# AccountBook
## 关于
基于Mirai2.0
需要配合 Mirai 插件 chat-command 使用
## 指令
- `+ [num]` 收入
- `+ [num] [reason]`
- `- [num]` 支出
- `- [num] [reason]`
- `=` 余额
- `= [days]` 过去 [days] 的流水记录
- `ab submit` 提交当日流水进数据库
- `ab setCheckoutDay [days]` 设置结算时间
- `ab add [QQ]` 添加白名单
- `ab del [QQ]` 移出白名单
- `ab list` 列出白名单
- `ab reload` 重载配置文件
</br></br>
以下命令仅限控制台使用
- `ab setBotQQ [QQ]` 设置机器人QQ
- `ab setOwner [QQ]` 设置插件使用者