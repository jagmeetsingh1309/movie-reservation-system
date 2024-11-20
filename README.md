## Movie Reservation System
Project Link - https://roadmap.sh/projects/movie-reservation-system

### Features
- ### Authentication and Authorization
  - User Authentication with JWT tokens and Role Base Authorization.
  - Roles (SUPER_ADMIN, ADMIN, USER)
  - SUPER_ADMIN can promote any user to ADMIN using endpoint 
- #### Movie Management
  - Super admin and admin can perform CRUD operations for Movie
  - User can filter movies by name, genre and language
  - Paginated response for optimization
- #### Show Management
  -  CRUD operations on Show can be performed by Super admin and Admin.
  - Users can filter shows by movie, theater and showDate
- #### Reservation Management
    - Users can reserve multiple seats for a specific show
    - Used Java's Reenterant Locks on individual Seat to avoid multiple booking
    - Users can view all their reservations
    - Users can cancel their upcoming reservations
    - Admin can view all reservations including revenue statistics

### API Endpoints
The application provides several endpoints for the functionalities mentioned above. Below are some key endoints. </br>
- <b>Signup User</b>`POST /auth/signup`
- <b>Authenticate User</b>`POST /auth/authenticate`
- <b>Create Movie</b>`POST /api/v1/movies/create`
- <b>Create Show</b>`POST /api/v1/shows/create`
- <b>Add Reservation</b>`POST /api/v1/reservations/reserve`
- <b>Cancel Upcoming Reservation</b>`POST /api/v1/reservations/cancel`
- <b>Promote User to Admin</b>`POST /api/v1/users/user/promote/{username}`
