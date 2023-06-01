# BraviTestApp
Teste técnico para Desenvolvedor Mobile Android

# Screenshots
![1](https://github.com/waister/BraviTestApp/assets/1563164/e461bdb0-6436-4d81-bd0c-ed6f3a592221) ![2](https://github.com/waister/BraviTestApp/assets/1563164/f49c06ac-9c28-498e-91de-5bf943d11ae5)


Este é um projeto desenvolvido como etapa técnica para a vaga na empresa Bravi. O objetivo é criar um aplicativo que sugere atividades para o usuário em momentos de tédico, usando as especificações fornecidas.

# Tecnologias utilizadas
* Linguagem: Kotlin;
* Arquitetura: MVVM.

# Funcionalidades
* Carregamento aleatório de atividades;
* Utilização do Shimmer enquanto o usuário aguarda o carregamento;
* Possibilidade de iniciar, desistir e finaliar uma atividade;
* Tela com o histórico das atividades realizadas e abandonadas.

# Ferramentas
* Retrofit: utilizado para fazer as requisições http de forma segura e com robustês;
* Coroutines: utiliado para fazer o carregamento assincono de taferas pesadas e melhorar o tratamento de possíveis excessões;
* Shimmer: utilizado como uma opção de harmonizar o carregamento de conteúdo, há estudos que afirmam que ele reduz a impressão de demora para o usuário;
* Koin: utilizado para fazer as injeções de dependência visando o desempenho e otimização dos códigos;
* Realm: utilziado para fazer o persistência das atividades iniciadas pelo usuário. Alem de ser um poderoso banco de dados, o Realm permite gerenciar os dados com orientação a objetos;

# Desempenho
* Foi utilizado o RecyclerView no histórico para melhorar o desempenho caso haja uma quantidade grande de registros.

# Contribuição
Contribuições são bem-vindas! Se você identificar algum problema, bug ou tiver alguma sugestão de melhoria, sinta-se à vontade para abrir uma issue.

# Licença
Este projeto está licenciado sob a MIT License.
