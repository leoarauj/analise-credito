# Análise de crédito

Aplicação de análise de crédito baseado nos dados cadastrais do cliente.

Tecnologias
---------------
JAVA + Spring Boot + Lombok + MapStruct + MySQL + Swagger.

Estrutura
---------------

API - Cadastro executa na porta [http://localhost:8080](http://localhost:8080)
> Responsável por executar a lógica de negócio para o cadastro, validação e consulta do Cliente.

API - Análise de crédito executa na porta [http://localhost:8081](http://localhost:8081)
> Responsável por executar a lógica de negócio do motor de análise de crédito. A API - Análise de crédito consome os serviços de Cadastro para realizar os calculos de risco e estipular limite máximo e mínimo de crédito em caso de aprovação.

Fórmula do cálculo do motor de análise de crédito
---------------
LIMITE MÁXIMO = (RENDA - (RENDA * (DEPENDENTES * 5%)) - 40%

LIMITE MÍNIMO = (RENDA - (RENDA * (DEPENDENTES * 5%)) - 20%

Observações - Análise de Crédito
---------------
Após a importação do projeto é necessário realizar o Clean Install com o Maven, para gerar as classes de implentações Mapper(Adapter), que realiza a conversão de Entity para DTO.

Na pasta /src/main/resources/SQL consta os scripts para popular o banco com os pesos para o cálculo da análise de risco.
