<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>


<div class="container-fluid m-0 p-0">

	<header>
		<nav class="navbar navbar-expand-lg sticky-top navbar-dark bg-dark">

			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarTogglerDemo03"
				aria-controls="navbarTogglerDemo03" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<a class="navbar-brand" href="connexion"><img

				src="../img/logo.jpg"
				width="30" height="30" alt="Accueil">
			</a>

			<div class="collapse navbar-collapse" id="navbarNav">

				<ul class="navbar-nav text-uppercase">
					<li class="nav-item active"><a class="nav-link" href="connexion">Accueil<span
							class="sr-only">(current)</span></a></li>
					<li class="nav-item"><a class="nav-link" href="afficherConges">Gestion
							des absences</a></li>
					<li class="nav-item"><a class="nav-link" href="planning-abs.jsp">Planning
							des absences</a></li>
					<li class="nav-item"><a class="nav-link" href="jours-feries.jsp">Jours fériés</a></li>
				</ul>

			</div>

			<div class="mr-sm-3 text-info d-flex align-items-center">
				<div>
					<i data-feather="user"></i>
				</div>
				<div>
					bonjour <span class="user-name"><%=(String)session.getAttribute("prenom")%></span>
				</div>

				<form method="GET" action="../connexion">
					<button class="btn btn-sm btn-none" type="submit">
						<i data-feather="log-out"></i>
					</button>
				</form>
			</div>

		</nav>

	</header>

</div>