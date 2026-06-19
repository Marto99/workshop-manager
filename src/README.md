# Workshop Manager

Практична тема: система за управление на малка работилница, сервиз или училищна лаборатория.

## Защо покрива критериите

### Domain entities
1. UserAccount - потребител с роля MANAGER или TECHNICIAN.
2. Equipment - оборудване/машина.
3. Booking - резервация на оборудване.
4. WorkTask - задача към техник.
5. MaintenanceRequest - заявка за поддръжка.

Технически може да добавиш enum-и: UserRole, EquipmentStatus, BookingStatus, TaskStatus, Priority, Urgency.

### Relationships
- Booking -> Equipment: ManyToOne
- Booking -> UserAccount: ManyToOne
- WorkTask -> UserAccount assignedTo: ManyToOne
- MaintenanceRequest -> Equipment: ManyToOne

### Роли
- GUEST: вижда само home, login, register.
- MANAGER: CRUD за Equipment, създава задачи, вижда всичко.
- TECHNICIAN: стартира/завършва задачи, решава maintenance заявки.
- LOGGED USER: създава резервации и maintenance заявки.

### Валидни функционалности
1. Equipment CRUD: create, read, update, delete.
2. Booking: create booking + cancel booking.
3. WorkTask: create task + start + complete + delete.
4. MaintenanceRequest: create request + resolve request.

### Security без Spring Security
- При успешен login:
    - session.setAttribute("userId", user.getId());
    - session.setAttribute("userRole", user.getRole().name());
- При logout:
    - session.invalidate();
- С HandlerInterceptor:
    - ако няма userId, redirect към /login за protected routes.
    - ако route изисква MANAGER или TECHNICIAN, проверява userRole.
