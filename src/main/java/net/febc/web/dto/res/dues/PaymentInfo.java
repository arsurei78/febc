package net.febc.web.dto.res.dues;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
public class PaymentInfo {

    private Long id;
    private String name;
    private LocalDate date;
    private LocalDate joinAt;

    @QueryProjection
    public PaymentInfo(Long id,
                       String name,
                       LocalDate joinAt){
        this.id = id;
        this.name = name;
        this.joinAt = joinAt;
    }

    @QueryProjection
    public PaymentInfo(Long id,
                       LocalDate date) {
        this.id = id;
        this.date = date;
    }

    public PaymentInfo(PaymentInfo info,
                       LocalDate localDate) {
        this.id = info.getId();
        this.name = info.getName();
        this.date = localDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentInfo)) return false;
        PaymentInfo member = (PaymentInfo) o;
        return Objects.equals(id, member.id) &&
                Objects.equals(date, member.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date);
    }
}
