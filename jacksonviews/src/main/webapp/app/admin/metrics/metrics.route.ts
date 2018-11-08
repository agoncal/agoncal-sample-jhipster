import { Route } from '@angular/router';

import { JacMetricsMonitoringComponent } from './metrics.component';

export const metricsRoute: Route = {
    path: 'jac-metrics',
    component: JacMetricsMonitoringComponent,
    data: {
        pageTitle: 'metrics.title'
    }
};
