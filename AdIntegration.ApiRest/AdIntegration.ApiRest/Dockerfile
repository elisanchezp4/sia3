#See https://aka.ms/containerfastmode to understand how Visual Studio uses this Dockerfile to build your images for faster debugging.

FROM mcr.microsoft.com/dotnet/aspnet:6.0 AS base
WORKDIR /app
EXPOSE 80
#EXPOSE 443

RUN apt-get update \
    && apt-get install -y --no-install-recommends \
        libldap-2.4-2 \
    && rm -rf /var/lib/apt/lists/*

FROM mcr.microsoft.com/dotnet/sdk:6.0 AS build
WORKDIR /src
COPY ["AdIntegration.ApiRest/AdIntegration.ApiRest.csproj", "AdIntegration.ApiRest/"]
RUN dotnet restore "AdIntegration.ApiRest/AdIntegration.ApiRest.csproj"
COPY . .
WORKDIR "/src/AdIntegration.ApiRest"
RUN dotnet build "AdIntegration.ApiRest.csproj" -c Release -o /app/build

FROM build AS publish
RUN dotnet publish "AdIntegration.ApiRest.csproj" -c Release -o /app/publish /p:UseAppHost=false

FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "AdIntegration.ApiRest.dll"]