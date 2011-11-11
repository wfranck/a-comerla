package models;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.data.validation.Min;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.db.jpa.Model;

public class DeliveryOrderResult extends Model {

    @OneToOne
    @JoinColumn(name = "OrderId", nullable = false)
    @Required
    public DeliveryOrder order;

    @ManyToOne
    @JoinColumn(name = "UserId", nullable = false)
    @Required
    public User caller;

    @ManyToOne
    @JoinColumn(name = "UserId", nullable = false)
    @Required
    public User payer;

    @OneToMany
    @JoinTable(name = "PeopleForOrderResult", joinColumns = @JoinColumn(name = "UserId"), inverseJoinColumns = @JoinColumn(name = "OrderResultId"))
    @Required
    @MinSize(1)
    public final List<User> people;

    @Column(name = "Total", nullable = false)
    @Required
    @Min(0.1)
    public final BigDecimal total;

    public DeliveryOrderResult(final DeliveryOrder order, final User caller, final User payer,
            final List<User> people, final BigDecimal total) {
        this.order = order;
        this.caller = caller;
        this.payer = payer;
        this.people = people;
        this.total = total;
    }

}
