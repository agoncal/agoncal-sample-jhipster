import { Route } from '@angular/router';

import { NoaMetricsMonitoringComponent } from './metrics.component';

export const metricsRoute: Route = {
    path: 'noa-metrics',
    component: NoaMetricsMonitoringComponent,
    data: {
        pageTitle: 'metrics.title'
    }
};
